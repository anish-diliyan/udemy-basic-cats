package part0_overview

object Ex4Overview extends App {
  // 1. Functor:-- "mappable" data structure
  trait MyFunctor[F[_]] {
    // it will take two parameters
    // 1. (fa: F[A]): value fa that is a type of F[A]
    // 2. (f: A => B): function f that converts A into B
    def map[A, B](initialValue: F[A])(f: A => B): F[B]
  }



}
