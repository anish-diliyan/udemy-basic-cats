package part4_monads

import cats.Monad
import cats.implicits.toFlatMapOps
import cats.implicits._
import part4_monads.Ex3Monad.MOption.MSome

object Ex3Monad {
  sealed trait MOption[+A]

  object MOption {
    case class MSome[+A](a: A) extends MOption[A]
    case object MNone extends MOption[Nothing]

    implicit val monadMOption: Monad[MOption] = new Monad[MOption] {
      override def pure[A](x: A): MOption[A] = MSome(x)

      override def flatMap[A, B](fa: MOption[A])(f: A => MOption[B]): MOption[B] = {
        fa match {
          case MSome(a) => f(a)
          case MNone => MNone
        }
      }

      // Monad extends --> Applicative extends --> Functor so we must have a map method
      // and we should be applicable to implement map using pure and flatMap
      override def map[A, B](fa: MOption[A])(f: A => B): MOption[B] = {
        flatMap(fa)(a => pure(f(a)))
      }

      override def flatten[A](ffa: MOption[MOption[A]]): MOption[A] = {
        flatMap(ffa)(identity) // identity: x => x
      }

      override def tailRecM[A, B](a: A)(f: A => MOption[Either[A, B]]): MOption[B] = ???
    }
  }

  def main(args: Array[String]): Unit = {
    val x: MOption[Int] = Monad[MOption].pure(5)
    println(x)
    val y: MOption[Int] = Monad[MOption].pure(6)
    val result = x.flatMap(xele => y.flatMap(yele => MSome(yele + xele)))
    println("result1 is: "+ result)

    val result2 = for {
      xele <- x
      yele <- y
    } yield xele + yele
    println("result2 is: " + result2)
  }
}
