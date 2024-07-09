import cats.Monad
import cats.implicits.{toFlatMapOps, toFunctorOps}

import scala.annotation.tailrec

sealed trait MayBe[+A]
case class Yes[+A](value: A) extends MayBe[A]
case object No extends MayBe[Nothing]

implicit val mayBeMonad: Monad[MayBe] = new Monad[MayBe] {
  def pure[A](x: A): MayBe[A] = Yes(x)

  def flatMap[A, B](fa: MayBe[A])(f: A => MayBe[B]): MayBe[B] = fa match {
    case Yes(a) => f(a)
    case No => No
  }

  @tailrec
  def tailRecM[A, B](a: A)(f: A => MayBe[Either[A, B]]): MayBe[B] = f(a) match {
    case Yes(Left(a1)) => tailRecM(a1)(f)
    case Yes(Right(b)) => Yes(b)
    case No => No
  }

  // override map for this MayBe, Monad -> Applicative -> Functor has map
  override def map[A, B](fa: MayBe[A])(f: A => B): MayBe[B] = {
    flatMap(fa)(a => pure(f(a)))
  }
}

val x: MayBe[Int] = Monad[MayBe].pure(42)
val y: MayBe[Int] = Monad[MayBe].pure(43)

val sum = x.flatMap(a => y.map(b => a + b))
println(sum)

// for comprehension
val sumFor = for {
  a <- x
  b <- y
} yield a + b
println(sumFor)

