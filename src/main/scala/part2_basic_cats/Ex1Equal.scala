package part2_basic_cats

/*
  Example of Type cass Eq: Eq is a pretty simple type class which allow us to compare values at
  compile time and make the code not compile if the values are of different types.
 */
object Ex1Equal extends App {
  // this is valid expression for scala, however it will always be false.
  // because you are comparing an int and string.
  val aComparision = 2 == "a string"

  // lets convert this this in compilation error using cats, using following steps:

  // 1. define the type class: cats already defined the type class so import
  import cats.Eq

  // 2. create implicit instance of type class that you need: cats already defined so import
  import cats.instances.int._

  // 3. offer some api to use this type class: here apis is already created by cat so use it
  // 3.1: to use the apis we need instance of type class
  // 3.2: then call apis on that instance
  val intEquality = Eq[Int]
  val aTypeSafeComparision: Boolean = intEquality.eqv(2, 3) // false
  //val anUnsafeComparision = intEquality.eqv(1, "a string") // compilation error

  // 4. extending the existing type via extension method: cats already have so use it
  import cats.syntax.eq._
  val anotherTypeSafeComparision = 2 === 3 // false
  val neqComparision = 2 =!= 3 // true
  //val invalidCompariosn = 2 === "a" // -- does not compile

  // extending the type class operation for composite type. e.g == list
  import cats.instances.list._ // we bring Eq[List] in scope
  // Note:-- extension methods are only visible in the presence of right type class instance
  val listCompariosn = List(2) === List(3) // false

  // we have the type class and some imported instance of type like int and list, what if
  // type class instance for type is not supported for ex: ToyCar

  case class ToyCar(model: String, price: Double)

  // create a type class instance for custom type, that is not supported by cat
  implicit val toyCarEq: Eq[ToyCar] = Eq.instance[ToyCar]{ (car1, car2) =>
    car1.price == car2.price
  }

  val compareToyCars = ToyCar("ferrari", 29.99) === ToyCar("lamborghini", 29.99) // true

}
