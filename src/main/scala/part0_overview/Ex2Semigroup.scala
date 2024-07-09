package part0_overview

import cats.implicits.catsSyntaxSemigroup
import cats.kernel.Semigroup

object Ex2Semigroup extends App {
  case class Person(name: String, age: Int)
  val person1 = Person("Anish", 10)
  val person2 = Person("Manish", 20)

  implicit val semiGroup: Semigroup[Person] = new Semigroup[Person] {
    def combine(x: Person, y: Person): Person = {
      Person(x.name + y.name, x.age + y.age)
    }
  }
  println(Semigroup[Person](semiGroup).combine(person1, person2)) // Person(AnishManish,30)
  println(person1.combine(person2)) // Person(AnishManish,30)
  println(person1 |+| person2) // Person(AnishManish,30)
}
