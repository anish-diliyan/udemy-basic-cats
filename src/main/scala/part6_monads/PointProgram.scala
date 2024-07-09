package part6_monads

import part6_monads.fblibrary._
import scala.annotation.tailrec


object PointProgram {

  def createDescription(args: Array[String]): Description[Unit] = Description.create {
    display(hyphens)
    display(question)

    val input: String = prompt()
    val integerAmount: Int = convertStringToInt(input)
    val positiveAmount: Int = ensureAmountIsPositive(integerAmount)
    val balance: Int = round(positiveAmount)
    val message: String = createMessage(balance)

    display(message)
    display(hyphens)
  }

  private val hyphens: String = "\u2500" * 50
  private val question: String = "How much do you want to deposit?"

  // side-effect(writing to the console)
  private def display(str: Any): Unit = println(str)

  // side-effect(reading from the console)
  //private def prompt(): String = scala.io.StdIn.readLine()

  private def prompt(): String = "100"

  // potential side-effect: failure(throwing an number format exception)
  private def convertStringToInt(str: String): Int = str.toInt

  private def ensureAmountIsPositive(amount: Int): Int = if (amount < 1) 1 else amount

  @tailrec
  private def round(amount: Int): Int = {
    if (isDivisibleByHundred(amount)) amount
    else round(amount + 1)
  }

  private def isDivisibleByHundred(amount: Int): Boolean = amount % 100 == 0

  private def createMessage(balance: Int): String = s"Congratulations! You now have USD $balance."
}
