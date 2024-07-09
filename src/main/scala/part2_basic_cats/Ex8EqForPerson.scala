package part2_basic_cats

object Ex8EqForPerson extends App {
  // step 1: import type class Eq
  import cats.Eq

  case class Person(name: String, age: Int)
  // step 2: create instance of Eq for Person
  implicit val fullEqualForPerson: Eq[Person] = new Eq[Person] {
    override def eqv(x: Person, y: Person): Boolean = {
      x.name == y.name && x.age == y.age
    }
  }

  // step 3: get instance of Eq[Person]
  val peronEq = Eq[Person]

  val result = peronEq.eqv(Person("anish", 30), Person("manish", 25))

  println(result)
}
