package part3_kinds

import scala.util.{Success, Try}

object Ex2Functor extends App {
  // pure functional programming deals with immutable values, so if you want to transform
  // a data structure we will need to create another one, not modify the old data structure.
  val aList = List(1, 2, 3).map(x => x * 10)
  // List(2, 3, 4) and will be completely new data structure,
  // here List(1, 2, 3) will not be modified.

  //Options
  val aOption: Option[Int] = Some(2)
  //Try
  val aTry: Try[Int] = Success(42)

  // when we apply map on aOption and on aTry it will generate new Option and Try
  aOption.map(x => x * 10)
  aTry.map(x => x * 10)

  // Functors
  // transform aList, aOption, aTry by multiplying 10 with its inside value.

  def do10xList(aList: List[Int]): List[Int] = aList.map(x => x * 10)
  def do10xOption(aOption: Option[Int]): Option[Int] = aOption.map(x => x * 10)
  def do10xTry(aTry: Try[Int]): Try[Int] = aTry.map(x => x * 10)

  // if we notice, their is a code duplication
}
