package part3_kinds

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/*
 Monads Every Where
 Monads are types which can take some values and do something interesting with them
 in a certain structure so 99% of the value of monads lies in their structure.
 */
object Ex5Monad extends App {
 /*
   Assume for example that i want to define a class which prevents multi threaded access
   to a value
  */
  // make this private so it can not be accessed from outside
  case class SafeValue[+T](private val internalValue: T) {
    /*
     This does not really do too much, but just assume that
     this get method does something very interesting
     I have tried to make this interesting by wrapping internalValue into synchronized block
     to prevent multi threaded access to this internalValue.
    */
    // This get method is a placeholder for something interesting that will return a T
    def get: T = synchronized(internalValue)

//    def transform[S](transformer: T => SafeValue[S]): SafeValue[S] = synchronized {
//      transformer(internalValue)
//    }
    def flatMap[S](transformer: T => SafeValue[S]): SafeValue[S] = synchronized {
       transformer(internalValue)
    }
  }

  // Assume the implementation here is unknown to you, and you only have access to this method
  // in some form of an external api(method)
  def giveMeSafeValue[T](value: T): SafeValue[T] = SafeValue(value)

  val safeString: SafeValue[String] = giveMeSafeValue("Scala is awesome")
  // extract
  val string = safeString.get
  // transform
  val upperString = string.toUpperCase()
  // wrap
  val upperSafeString = SafeValue(upperString)

  // ETW PATTERN
//  val upperSafeString2 = safeString.transform(s => SafeValue(s.toUpperCase))
  val upperSafeString2: SafeValue[String] = safeString.flatMap(s => SafeValue(s.toUpperCase))

  // If you have this pattern, you basically created the condition for a monad.
  // And a Monad is a type which I called here SafeValue for an example and
  // the monad has two fundamental operations
  // 1. Ability to wrap a value which is in my case a plane value of Type: T into
  // my more interesting type SafeValue.
  // In object oriented world this is just a constructor. but in function programming
  // we call this pure or unit.
  // 2. Ability to transform one of these SafeValue into another kind of SafeValue
  // based on a transformation function that looks like "transformer: T => SafeValue[S]"
  // in scala we call this bind or flatMap.
  // so I am simply going to rename "transform into flatMap"
  // Till now we have basically created a condition for a Monad with a
  // constructor that is called pure and a flatMap function

  // let me give some examples from real life just to reinforce this pattern

  // Example 1:
  // lets assume that you are populating a database with people and you have some
  // code that looks like this:
  case class Person(firstName: String, lastName: String){
    // we want first name and last name should not be null, so if you create a person with
    // null value then this assertion will throw an exception
    assert(firstName != null && lastName != null)
  }
  // java.lang.AssertionError: assertion failed, but we do not want the exception,
  // so you need to ensure firstName and lastName is not null.
  //Person(null, "Pandey")

  // lets assume we have some api (calling census api) in your application
  // the trouble is firstName and lastName is being populated from outside which may be null
  // and its your job to test whether they are null or not.
  def getPerson(firstName: String, lastName: String): Person = {
    // the general way of dong this is write some defensive code
    if(firstName != null){
      if(lastName != null) Person(firstName, lastName)
      else null
    } else null
    // Notice the pattern here, we extract the firstName and lastName check if not null
    // then return instance of Person with first and last name
    // in simple checking condition and wrapping the value in Person
  }

  def getPersonBetter(firstName: String, lastName: String): Option[Person] = {
    Option(firstName).flatMap{ fName =>
      Option(lastName).flatMap{ lName =>
       Option(Person(fName, lName))
      }
    }
  }

  def getPersonBetterV2(firstName: String, lastName: String): Option[Person] = for {
    fName <- Option(firstName)
    lName <- Option(lastName)
  } yield Person(fName, lName)

  // Example 2:
  // assuming you are calling some async api for your online store like fetching from some
  // external resource
  case class User(id: String)
  case class Product(sku: String, price: Double)

  // lets assume your online store has two external apis
  def getUser(url: String): Future[User] = Future{
    User("anish") // sample implementation
  }
  def getLastOrder(userId: String): Future[Product] = Future {
    Product("123-456", 99.99) // sample implementation
  }

  val anishUrl = "my.store.com/users/anish"

  // ETW PATTERN
  val vatIncludedPrice: Future[Double] = getUser(anishUrl).flatMap{ user =>
    getLastOrder(user.id).map(product => product.price * 1.19)
  }

  val vatIncludedPriceFor = for {
    user <- getUser(anishUrl)
    product <- getLastOrder(user.id)
  } yield product.price * 1.19


  // Example 3:
  // Like Option and Future scala list also follow the ETW PATTERN
  val numbers = List(1, 2, 3)
  val chars = List('a', 'b', 'c')

  // get a value from numbers: extract
  // get a value from chars: extract
  // concat each element of both list: cartesian product: transform
  // wrap the result in another list

  // our requirement is solvable using ETW pattern and scala list is Monad so it must support

  val product: List[(Int, Char)] = numbers.flatMap(number => chars.map(char => (number,char)))

  val productFor = for {
    number <- numbers
    char <- chars
  } yield (number, char)

  // Till now we understand: Whenever you need to extract transform and wrap you probably
  // need a Monad, which needs a constructor called unit
  // and a transformation function is called flatMap

  // Properties of Monad
  // Monad has a constructor and flatMap function but these two need to work in a certain way

  // property 1: left identity (Monad(x).flatMap(f) === f(x))
  def twoConsecutive(x: Int) = List(x, x+1)
  twoConsecutive(3) // List(3,4)
  List(3).flatMap(twoConsecutive) // List(3,4)

  // property 2: right identity-> USELESS -> Monad(v).flatMap(x => Monad(x)) === Monad(v)
  List(1, 2, 3).flatMap(x => List(x)) // List(1, 2, 3)

  // property 3: Associativity -> that is implementing the ETW pattern over and over again
  // Monad(v).flatMap(f).flatMap(g) == Monad(v).flatMap(x => f(x).flatMap(g))
  // It does not really matter in which order you apply the functions as long as
  // function f applied on every element before the function g applied on every element.
  val myNumbers = List(1, 2, 3)
  val incrementer = (x: Int) => List(x , x + 1)
  val doubler = (x: Int) => List(x, x * 2)

  val result1 = myNumbers.flatMap(incrementer).flatMap(doubler)
  val result2 = myNumbers.flatMap(x => incrementer(x).flatMap(doubler))

  println(result1) // List(1, 2, 2, 4, 2, 4, 3, 6, 3, 6, 4, 8)
  println(result2) // List(1, 2, 2, 4, 2, 4, 3, 6, 3, 6, 4, 8)
  /*
     List(
       incrementer(1).flatMap(doubler) --- 1, 2, 2, 4
       incrementer(1).flatMap(doubler) --- 2, 4, 3, 6
       incrementer(1).flatMap(doubler) --- 3, 6, 4, 8
     )
   */
  println(result1 == result2) // true

}
