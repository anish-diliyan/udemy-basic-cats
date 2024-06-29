package part0_overview

object Ex1Overview extends App {
   /*
     Semigroup ---> Monoid


     Functor
    */

  // Semigroup has combine function, this can be used in very general way to
  // combine two elements of the the same type,
  // if you have Semigroup in scope you can call combine method and obtain result
  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def combine(x: A, y: A): A
    // besides the combine function that a Semigroup has, Monoid also has
    // a method that defines a neutral element for that operation this is called empty.
    def empty: A
    // empty is the neutral of that combination function such that if you combine any element
    // with empty you will return that same element. so in the case of an Integer multiplication
    // the empty element would be the number one. because multiplying any number with 1
    // will give the same number.
  }

  // functor has a very practical application, it denotes the capability
  // of mapping wrapped values
  trait Functor[F[_]]{
    // it will take two parameters
    // 1. (fa: F[A]): value fa that is a type of F[A]
    // 2. (f: A => B): function f that converts A into B
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  trait Applicative[F[_]] extends Functor[F] {
    // lift a pure value A into wrapped value F[A]
    def pure[A](a: A): F[A]
  }

  trait FlatMap[F[_]] extends Functor[F]{
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  }

  trait Monad[F[_]] extends Applicative[F] with FlatMap[F] {}

  trait Semigroupal[F[_]]{
    // do cross product
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  }

  trait Apply[F[_]] extends Semigroupal[F] with Functor[F]{
    // this is named ap instead of apply, because apply has special meaning in scala
    // it will apply function A => B on
    // ap has the capability of invoking A => B function wrapped in F over
    // the value A wrapped in F.
    def ap[A, B](fab: F[A => B], fa: F[A]): F[B]
  }
}
