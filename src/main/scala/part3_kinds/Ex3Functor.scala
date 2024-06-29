package part3_kinds

import cats.Functor

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object Ex3Functor extends App {

  class Secret[A](val value: A) {
    private def hashed: String = {
      val s = value.toString
      val bytes = s.getBytes(StandardCharsets.UTF_8)
      val d = MessageDigest.getInstance("SHA-1")
      val hashBytes = d.digest(bytes)
      new String(hashBytes, StandardCharsets.UTF_8)
    }
    override def toString: String = hashed
  }

  object Secret {
    implicit val secretFunctor: Functor[Secret] = new Functor[Secret] {
      override def map[A, B](fa: Secret[A])(f: A => B): Secret[B] =
        new Secret(f(fa.value))
    }
  }

  val anishSecret = new Secret[String]("Anish")
  println("anish secret is: " + anishSecret) // |QI�{�M��Nm� ��\[�
  println("anish secret value is: " + anishSecret.value) // Anish

  val upperAnishSecret = Functor[Secret].map(anishSecret)(_.toUpperCase)
  println("upper anish secret is: " + upperAnishSecret) // ��y��p��R��� k�6
  println(upperAnishSecret.value) // ANISH

  val optionFunctor: Functor[Option] = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
      case None => None
      case Some(value) => Some(f(value))
    }
  }

  val some: Option[Int] = Some(3)
  val someFunctorResult = optionFunctor.map(some)(ele => ele * 3)
  println("option functor result is: " + someFunctorResult) // Some(9)
  val none: Option[Int] = None
  val noneFunctorResult = optionFunctor.map(none)(ele => ele * 3)
  println("none functor result is: " + noneFunctorResult) // None

  val listFunctor: Functor[List] = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa match {
      case Nil => Nil
      case hd :: tl => f(hd) :: map(tl)(f)
    }
  }

  val empty: List[Int] = Nil
  val nilFunctorResult = listFunctor.map(empty)(ele => ele * 3)
  println("nil functor result is: " + nilFunctorResult) // List()
  val oneToThree: List[Int] = List(1, 2, 3)
  val oneToThreeFunctorResult = listFunctor.map(oneToThree)(ele => ele * 3)
  println("one to thee functor result is: " + oneToThreeFunctorResult) // List(3, 6, 9)
}
