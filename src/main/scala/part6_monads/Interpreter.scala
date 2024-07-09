package part6_monads

import part6_monads.fblibrary._

object Interpreter {
  def main(args: Array[String]): Unit = {
    print(Console.RED)
    val description: Description[Unit] = PointFreeProgram.createDescription(args)

    def interpret[A](description: Description[A]):  A = description.apply()
    print(Console.GREEN)
    interpret(description)
    print(Console.RESET)
  }
}
