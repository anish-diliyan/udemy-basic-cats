package part2_basic_cats

import cats.Monoid

object Ex5Monoid extends App {
  case class Speed(miterPerSecond: Double){
    def kilometerPerSecond: Double = miterPerSecond / 1000.0
    def milesPerSecond: Double = miterPerSecond / 1609.34
  }

  object Speed {
    def addSpeed(s1: Speed, s2: Speed): Speed = Speed(s1.miterPerSecond + s2.miterPerSecond)

//    implicit val monoidSpeed: Monoid[Speed] = new Monoid[Speed] {
//      override def empty: Speed = Speed(0)
//      override def combine(x: Speed, y: Speed): Speed = addSpeed(x, y)
//    }

//    implicit val monoidSpeed: Monoid[Speed] = Monoid.instance(
//      Speed(0),
//      (s1, s2) => addSpeed(s1, s2)
//    )

    implicit val monoidSpeed: Monoid[Speed] = Monoid.instance(Speed(0), addSpeed)
  }

  val speed1 = Speed(1000.0)
  val speed2 = Speed(2000.0)

  val result1 = Monoid[Speed].combine(speed1, speed2)
  println(result1) // Speed(3000.0)

  val result2 = Monoid[Speed].empty
  println(result2) // Speed(0)

  val result3 = Monoid[Speed].combine(speed1, Monoid[Speed].empty)
  println(result3) // Speed(1000.0)

  import cats.implicits.catsSyntaxSemigroup
  val result4 = Speed(2000.0) |+| Speed(3000.0)
  println(result4) // Speed(5000.0)

  val result5 = Monoid[Speed].combineAll(List(speed1, speed2))
  println(result5)

  import cats.implicits.toFoldableOps
  val result6 = List(speed1, speed2).combineAll
  println(result6)

  // TODO : import is missing
  // val result7 = Monoid[Speed].isEmpty(speed1)

}
