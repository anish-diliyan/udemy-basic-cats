package type_classes.pattern

import type_classes.pattern.EqualTypeClass.{Equal, User}

object Ex2PimpMyLibStyle extends App {
   /*
     3. surface out the type class instance.
     When enriching a type class using the "pimp my library" approach,
     we can add a method that takes an implicit type class instance as a parameter.
     This allows us to call the method on our enriched types as if it were built in.
   */
   implicit class RichEqual[T](value: T) {
     def equal(user: T)(implicit instance: Equal[T]): Boolean = instance.===(user, value)
     def unequal(user: T)(implicit instance: Equal[T]): Boolean = instance.!==(user, value)
   }

  val user1 = User("Anish", 10)
  val user2 = User("Anish", 10)

  val equalResult = user1.equal(user2)
  //RichEqual[User](user1).equal(user2)
  println("equal result is: "+ equalResult)
  val notEqualResult = user1.unequal(user2)
  println("not equal result: " + notEqualResult)
}
