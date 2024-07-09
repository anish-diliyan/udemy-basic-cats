// Step 1:  create a type class
trait MyEqual[T] {
  def ===(obj1: T, obj2: T): Boolean
}

case class Person(name: String, age: Int)

// Step 2: create instance of type class for the type Person
implicit object FullEquality extends MyEqual[Person] {
  override def ===(obj1: Person, obj2: Person): Boolean = {
    obj1.name == obj2.name && obj1.age == obj2.age
  }
}

// Step 3: expose method to get instance of MyEqual[Person] type class
object MyEqual{
  def apply[T](implicit instance: MyEqual[T]): MyEqual[T] = instance
}

val anish = Person("anish", 30)
val manish = Person("manish", 25)

MyEqual[Person].===(anish, manish)