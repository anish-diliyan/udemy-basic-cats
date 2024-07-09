package part6_monads.fblibrary

object Description {
 def create[A](a: => A): Description[A] = () => a
}
