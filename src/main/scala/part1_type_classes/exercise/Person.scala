package type_classes.exercise

case class Person(name: String, id: Int)

object Person {
  // TODO #9:
  //  Define an Eq instance for Person comparing them by name
  //  Extra points: receive an implicit instance for String and use it
  implicit val eqPersonName: Eq[Person] =
    Eq.instance((fst, snd) => fst.name == snd.name)

  // TODO #10:
  //  Define an Eq instance for Person comparing them by id
  //  Extra points: receive an implicit instance for Int and use it
  implicit val eqPersonId: Eq[Person] = Eq.instance((fst, snd) => fst.id == snd.id)
}