package part0_overview

import cats.Monoid
import cats.implicits.catsSyntaxSemigroup

object Ex3Monoid extends App {
  implicit val monoid: Monoid[Int] = new Monoid[Int]{

    override def empty: Int = 1

    override def combine(x: Int, y: Int): Int = x * y
  }

  println(monoid.combine(5, 10)) // 50

  println(5.combine(10))
}
