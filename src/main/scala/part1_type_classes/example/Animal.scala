package part1_type_classes.example

sealed trait Animal
final case class Dog(name: String) extends Animal
final case class Cat(name: String) extends Animal
final case class Lion(name: String) extends Animal
