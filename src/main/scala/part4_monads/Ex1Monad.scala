package part4_monads

import cats.Monad
import cats.implicits.{toFlatMapOps, toFunctorOps}

import scala.annotation.tailrec


/*
 * Monads, are a fundamental concept in functional programming that represent computations
 * that can be composed and sequenced in a specific way.
 *
 * In Simple Words: A monad is like a special box or container that wraps a value. This box comes with
 * some rules and operations that allow you to work with the value inside
 * while keeping track of any side effects that may occur.
 *
 * Imagine you're ordering a package online, and relate Monad with the following:
 * package: is the value
 * shipping process: can have side effect.
 * shipping company: is monad that handles the package and shipping process and ensures safe delivery.
 *
 * The Cats library provides a powerful and consistent implementation of the Monad abstraction,
 * making it easier to work with monadic types in Scala.
 *
 *
 * A monadic type is any type that has an instance of the Monad type class from the Cats library.
 * Some common examples of monadic types in Scala include: Option, Either, List, Future e.t.c
 *
 * The Monad type class, which has three main operations:
 * 1. pure (for creating a new monadic value),
 * Option.pure(42) creates a Some(42) value.
 * 2. flatMap (for composing and sequencing monadic computations), and
 * Some(42).flatMap(x => Some(x * 2)) results in Some(84)
 * 3. tailRecM (for stack-safe monadic recursion). It takes an initial value and a function that returns
 * either a monadic value wrapped in
 * 1. Left (indicating that recursion should continue)
 * 2. Right (indicating that recursion should terminate).
 *
 * Whenever you need a ETW pattern in your code, you probably need a Monad:
 * 1. Extract : Extract the value from the context.
 * 2. Transform : Perform some operations or transformations on the extracted value.
 * 3. Wrap : Wrap the transformed value back into the context.
 *
 * Properties of Monad: Monad has a constructor and flatMap function but these two need to work in a
 * certain way.
 * 1. Left identity (Monad(x).flatMap(f) === f(x))
 * 2. Right identity -> USELESS -> Monad(v).flatMap(x => Monad(x)) === Monad(v)
 * 3. Associativity -> Monad(v).flatMap(f).flatMap(g) == Monad(v).flatMap(x => f(x).flatMap(g))
 * It does not really matter in which order you apply the functions as long as
 * function f applied on every element before the function g applied on every element.
 */

sealed trait MayBe[+A]
case class Yes[+A](value: A) extends MayBe[A]
case object No extends MayBe[Nothing]


object Ex1Monad extends App {
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
  println(sum) // Yes(85)

  // for comprehension
  val sumFor = for {
    a <- x
    b <- y
  } yield a + b
  println(sumFor) // Yes(85)
}
