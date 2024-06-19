package part2_basic_cats

import cats.Monoid

object Ex6Monoid extends App {
  val sumMonoid: Monoid[Int] = Monoid.instance(0, (a, b) => a + b)
  val minMonoid: Monoid[Int] = Monoid.instance(Int.MaxValue, (a, b) => a min b)
  def listMonoid[A]: Monoid[List[A]] = Monoid.instance(Nil, (a, b) => a ++ b)
  def monoidString: Monoid[String] = Monoid.instance("", _ + _)

  println(sumMonoid.combine(3, 4)) // 7
  println(minMonoid.combine(10, 2)) // 2
  // List(true, false, false, true)
  println(listMonoid.combine(List(true, false), List(false, true)))
  println(monoidString.combine("hello ", "world")) // hello world
}
