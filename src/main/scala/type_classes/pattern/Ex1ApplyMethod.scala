package type_classes.pattern

import type_classes.pattern.EqualTypeClass.{Equal, User}

object Ex1ApplyMethod extends App {
  /*
   3. surface out the type class instance.
   create a companion object of type class and implement apply method, which will allow us
   to use a type class instance without us knowing that it is there, this will take
   implementation of type class implicitly and return the instance
   */
  object Equal {
    def apply[T](implicit equal: Equal[T]): Equal[T] = equal
  }

  val user1 = User("Anish", 10)
  val user2 = User("Anish", 10)

  val equalResult = Equal[User].===(user1, user2)
  println("equal result is: "+ equalResult)
  val notEqualResult = Equal[User].!==(user1, user2)
  println("not equal result: " + notEqualResult)
}
