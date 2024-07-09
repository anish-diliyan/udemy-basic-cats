package part4_monads

/**
 * let's create a custom MOption monad using the Cats library.
 * We'll define a new data type MOption and implement the Monad instance for it.
 */

import cats.Monad

import scala.annotation.tailrec


sealed trait MOption[+A] {
  def flatMap[B](f: A => MOption[B]): MOption[B] = this match {
    case MSome(value) => f(value)
    case MNone => MNone
  }

  def map[B](f: A => B): MOption[B] = flatMap(a => MSome(f(a)))
}

case class MSome[A](value: A) extends MOption[A]
case object MNone extends MOption[Nothing]

object MOption {
  def pure[A](value: A): MOption[A] = MSome(value)

  implicit val mOptionMonad: Monad[MOption] = new Monad[MOption] {
    def pure[A](value: A): MOption[A] = MOption.pure(value)

    def flatMap[A, B](fa: MOption[A])(f: A => MOption[B]): MOption[B] = fa.flatMap(f)

    @tailrec
    def tailRecM[A, B](a: A)(f: A => MOption[Either[A, B]]): MOption[B] = {
      f(a) match {
        case MNone => MNone
        case MSome(Left(a1)) => tailRecM(a1)(f)
        case MSome(Right(b)) => MSome(b)
      }
    }
  }
}

/**
  Here's what we've done:
  We defined a sealed trait MOption[+A] to represent our custom monadic type. It has two case classes:
  MSome and MNone which are analogous to Some and None in Scala's standard Option type.

  We implemented the flatMap and map methods directly on the MOption trait. The flatMap
  method applies the given function f to the value inside MSome , or returns MNone if the instance is MNone.
  The map method is implemented in terms of flatMap. In the MOption companion object, we defined a
  pure method that creates a MSome instance from a given value.

  We defined an implicit Monad instance for MOption . This instance provides the pure, flatMap, and
  tailRecM methods required by the Monad type class.
*/

// Now, we can use our custom MOption monad just like we would use the standard Option monad:
object Ex4Monad extends App {
  // we create a MySome(3) instance using MyOption.pure(3)
  val result1 = MOption.pure(3).map(_ * 2)
  println(result1) // prints MySome(6)
  val result2 = result1.flatMap(x => MOption.pure(x + 4))
  println(result2) // prints MySome(10)
  val result3 = MNone.flatMap(x => MOption.pure(x))
  println(result3) // prints MNone
}