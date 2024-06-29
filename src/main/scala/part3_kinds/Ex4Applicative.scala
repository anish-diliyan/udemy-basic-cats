package part3_kinds

import cats.Applicative
import cats.implicits.{catsSyntaxTuple2Semigroupal, catsSyntaxTuple3Semigroupal, toFunctorOps}

object Ex4Applicative extends App {
  sealed trait Validated[+A]

  object Validated {
    case class Valid[+A](a: A) extends Validated[A]
    case class InValid(errors: List[String]) extends Validated[Nothing]

    implicit val applicative: Applicative[Validated] = new Applicative[Validated] {
      override def pure[A](x: A): Validated[A] = Valid(x)
      override def ap[A, B](vf: Validated[A => B])(va: Validated[A]): Validated[B] =
        (vf, va) match {
          case (Valid(f), Valid(a)) => Valid(f(a))
          case (InValid(e1), Valid(a)) => InValid(e1)
          case (Valid(f), InValid(e2)) => InValid(e2)
          case (InValid(e1), InValid(e2)) => InValid(e1 ++ e2)
        }
       // map2(vf, va)((f, a) => f(a))

      override def map2[A, B, C]
      (va: Validated[A], vb: Validated[B])
      (f: (A, B) => C): Validated[C] = {
//        (va, vb) match {
//          case (Valid(a), Valid(b)) => Valid(f(a, b))
//          case (InValid(e1), Valid(b)) => InValid(e1)
//          case (Valid(a), InValid(e2)) => InValid(e2)
//          case (InValid(e1), InValid(e2)) => InValid(e1 ++ e2)
//        }
        //val g: A => B => C = a => b => f(a, b)
        // g would be curried version of x
        //val g: A => B => C = f.curried
        ap(ap(pure(f.curried))(va))(vb)
      }
    }
  }

  val v1: Validated[Int]  = Applicative[Validated].pure(1)
  val v2: Validated[Int]  = Applicative[Validated].pure(2)
  val v3: Validated[Int]  = Applicative[Validated].pure(3)

  val map3Result = (v1, v2, v3).mapN((a, b, c) => a + b + c)
  println(map3Result) // Valid(6)

  val map2Result = (v1, v2).mapN((a, b) => a + b)
  println(map2Result) // Valid(3)

  val optionApplicative: Applicative[Option] = new Applicative[Option] {
    override def pure[A](x: A): Option[A] = Some(x)
    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = {
      (ff, fa) match {
        case (Some(f), Some(a)) => Some(f(a))
        case _ => None
      }
    }
  }

  val result1 = optionApplicative.map2(Some(3), Some(4))(_ + _)
  println(result1) // Some(7)

  //val none: Option[Int] = None
  //val result2 = optionApplicative.map2(none, Some(3))(_ + _)
  val result2 = optionApplicative.map2[Int, Int, Int](None, Some(3))(_ + _)
  println(result2) // None

  val listApplicative: Applicative[List] = new Applicative[List] {
    override def pure[A](x: A): List[A] = List(x)
    override def ap[A, B](ff: List[A => B])(fa: List[A]): List[B] = {
      (ff, fa) match {
        // if both list is non empty
        case (f :: fs, a :: as) => (a :: as).fmap(f) ++ ap(fs)(a :: as)
        // if any of the list is empty
        case _ => Nil
      }
    }
  }

  val addListCrossJoin = listApplicative.map2(List(1, 2, 3), List(4, 5))( _ + _)
  println(addListCrossJoin) // List(5, 6, 6, 7, 7, 8)

  val addListCrossJoinEmpty = listApplicative.map2[Int, Int, Int](List(), List(4, 5))( _ + _)
  println(addListCrossJoinEmpty) // List()
}
