package part1_type_classes.exercise

import type_classes.exercise.Person

trait Eq[A] {
  def eq(obj1: A, obj2: A): Boolean
}

object Eq {
  // TODO #2:
  //  Define the method 'apply' so we can summon instances from implicit scope
  def apply[A](implicit instance: Eq[A]): Eq[A] = instance

  // TODO #3:
  //  Define the method 'instance' so we can build instances of the Eq type class.
  //  It should take as the only parameter a function of type (A, A) => Boolean
  def instance[A](f: (A, A) => Boolean): Eq[A] = new Eq[A] {
    override def eq(fst: A, snd: A): Boolean = f(fst, snd)
  }

  // TODO #4:
  //  Define an Eq instance for String
  implicit object EqString extends Eq[String]{
    override def eq(obj1: String, obj2: String): Boolean = obj1 == obj2
  }

  // TODO #5:
  //  Define an Eq instance for Int
  implicit object EqInt extends Eq[Int]{
    override def eq(obj1: Int, obj2: Int): Boolean = obj1 == obj2
  }

  // TODO #6:
  //  Define an Eq instance for Person. equal if both their names and ids are equal.
  //  Extra points: receive implicit instances for String and Int and use them
  implicit object EqPerson extends Eq[Person]{
    override def eq(obj1: Person, obj2: Person): Boolean =
      Eq[String].eq(obj1.name, obj2.name) && Eq[Int].eq(obj1.id, obj2.id)
  }

  // TODO #7:
  //  Provide a way to automatically derive instances for Eq[Option[A]] given that
  //  we have an implicit instance for Eq[A]
  implicit def eqOpt[A](implicit eqA: Eq[A]): Eq[Option[A]] = instance[Option[A]] {
    case (Some(obj1), Some(obj2)) => eqA.eq(obj1, obj2)
    case (None, None) => true
    case _ => false
  }

  object Syntax {
    // TODO #8:
    //  Define a class 'EqOps' with a method 'eqTo' that enables the following
    //  syntax: "hello".eqTo("world")
    implicit class EqOps[A](a: A) {
      def eqTo(other: A)(implicit eqA: Eq[A]): Boolean = eqA.eq(a, other)
    }
  }
}
