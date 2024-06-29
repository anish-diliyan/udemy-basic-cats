package part3_kinds

/*
  Types: type in scala is organized into kinds and a kind is a type of types
 */
object Ex1Kinds extends App {
  // Int is a regular type that we can attach to a value
  // level-0 types: (Int String, Double, Person e.t.c..) you can attach these types to a value
  val aNumber: Int = 42
  case class Person(name: String, age: Int)
  val anish: Person = Person("Anish", 30)

  // "generic" = level-1 kind will take type argument of level-0 kind
  // level-0 can be attached to a value directly but level-1 can not be attached on its own
  // to a value.
  class LinkedList[T] {
    // code: the code we write here will work on any type that replaces T.
  }
  // can not be attached on it own
  // val aList: LinkedList = ??? this would be a compilation error, but rather i need to
  // pass a concrete type argument to LinkedList such as Int
  // here LinkedList[Int] is a level-0 type but LinkedList is a level-1 type.
  val aList: LinkedList[Int] = ???

  // Type Constructor: It is higher level type that receives another type and return another
  // concrete type. for ex: LinkedList it takes Int and return LinkedList[Int]

  // level-2 types: Also called higher kinded type
  // As we have seen in level-1 type we pass a level-0 type and create another type
  // this can be extended further to create a higher kinded type
  // for ex: Functor takes a type argument F which is itself generic(level-1) type.
  // in scala we denote that by place a underscore in between brackets.
  // _ -> level-0 type
  // F[_] -> level-1 type
  // Functor[F[_]] -> level-2 type (higher kinded type)
  class Functor[F[_]]
  // two create a level-0 type of Functor we need a level type,
  // and we know List is a level-1 type
  // here functorList is level-0 type
  val functorList = new Functor[List]

  // we can go beyond level-2 type
  class Meta[F[_[_]]] // level-3 type (but after level-2 it is almost not use)
  val metaFunctor = new Meta[Functor]

  // As of now we understood how types can be organized to their level i.e if types can take
  // other types as argument, and also know what is type constructor.
  // for example: LinkedList and Functor is a type constructor.

  // type which can take multiple multiple type argument and perhaps type arguments of
  // different type.
  class HashMap[K, V] // HashMap is a level-1 type constructor which takes two level-0 arguments
  val anAddressBook = new HashMap[String, String]

  class ComposedFunctor[F[_], G[_]] // level-2 type constructor which takes two level-1 argument
  val aComposedFunctor = new ComposedFunctor[List, Option]

  class Formatter[F[_], T] // level-??????, we should be at level-2 but not sure.
  val aFormatter = new Formatter[Option, String]






}
