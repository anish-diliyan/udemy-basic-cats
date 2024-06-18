package type_classes.pattern

object EqualTypeClass {
  /*
   1. defining a type class:
   This is a trait which takes a type parameter and define some operations
   */
  trait Equal[T]{
    def ===(obj1: T, obj2: T): Boolean
    def !==(obj1: T, obj2: T): Boolean
  }
  /*
   2. Creating the type class instance
   create a instance of type class by extending the type class trait
   and implement the operations for specific type.
   These instances are often created implicit
   */
  case class User(name: String, age: Int)
  implicit object FullEquality extends Equal[User]{
    override def ===(obj1: User, obj2: User): Boolean =
      obj1.name == obj2.name && obj1.age == obj2.age

    override def !==(obj1: User, obj2: User): Boolean =
      obj1.name != obj2.name || obj1.age != obj2.age
  }
}
