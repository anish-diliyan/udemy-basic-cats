package part6_monads

package object fblibrary {
  // take unit and return something is called a Thunk
  private type Thunk[A] = () => A
  type Description[A] = Thunk[A]

  private type RegularArrow[A, B] = A => B
  type KleisliArrow[A, B, C[_]] = A => C[B]
}
