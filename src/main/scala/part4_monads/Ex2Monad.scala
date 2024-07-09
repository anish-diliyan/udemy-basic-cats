package part4_monads

/**
 * 1. What is a Monad?
 *    - A Monad is a way to structure and sequence computations that may involve side effects or
 *      additional context.
 *    - It provides a way to chain operations together, passing the result of one operation as the input to
 *      the next.
 *
 * 2. The Monad Type Class
 *    - In Cats, the `Monad` type class defines two main operations: `pure` and `flatMap`.
 *    - `pure` takes a value and wraps it in the monadic context (e.g., `Option`, `List`, `Future`).
 *    - `flatMap` is used to chain monadic operations together. It takes a monadic value and a function
 *       that returns another monadic value, and flattens the nested structure.
 *
 * 3. Monadic Operations
 *    - `pure` is like creating a box with a value inside.
 *    - `flatMap` is like opening a box, applying a function to the value inside, and putting the result in
 *       a new box.
 *    - You can chain multiple `flatMap` calls together, like opening nested boxes one by one.
 *
 * 4. Monadic Laws
 *    - Monads follow three laws: left identity, right identity, and associativity.
 *    - These laws ensure that monadic operations compose predictably and behave as expected.
 *
 * 5. Common Monads in Scala
 *    - `Option` is a monad that represents the possibility of a value being present or absent.
 *    - `List` is a monad that represents a sequence of values.
 *    - `Future` is a monad that represents an asynchronous computation that may produce a value in the future.
 *    - `Either` is a monad that represents a value that can be either a success or an error.
 *
 * 6. Using Monads with for-comprehensions
 *    - Cats provides syntax for working with monads using `for` comprehensions, which can make code more
 *      readable and concise.
 *    - For example, you can chain multiple `flatMap` operations using a `for` comprehension:
 *
 * 7. Benefits of Monads
 *    - Monads help structure code in a composable and declarative way, making it easier to reason about and
 *      maintain.
 *    - They provide a consistent way to handle effects like exceptions, state, and asynchronous computations.
 *    - Monads encourage a functional programming style, which can lead to more modular and testable code.
 */

import cats.Monad
import cats.instances.option._ // for Monad instance of Option
import cats.instances.list._ // for Monad instance of List

object Ex2Monad extends App {

  // Using Option as a Monad
  val optionMonad = Monad[Option]

  val opt1: Option[Int] = Some(3)
  val opt2: Option[Int] = None

  // flatMap using Monad's flatMap
  val optionResult1 = optionMonad.flatMap(opt1)(a => Some(a * 2)) // Some(6)
  val optionResult2 = optionMonad.flatMap(opt2)(a => Some(a * 2)) // None

  // pure using Monad's pure
  val optionResult3 = optionMonad.pure(42) // Some(42)

  println(optionResult1)
  println(optionResult2)
  println(optionResult3)

  // Using List as a Monad
  val listMonad = Monad[List]

  val list1 = List(1, 2, 3)
  val list2 = List.empty[Int]

  // flatMap using Monad's flatMap
  val listResult1 = listMonad.flatMap(list1)(a => List(a * 2, a * 3)) // List(2, 3, 4, 6, 6, 9)
  val listResult2 = listMonad.flatMap(list2)(a => List(a * 2, a * 3)) // List()

  // pure using Monad's pure
  val listResult3 = listMonad.pure(42) // List(42)

  println(listResult1)
  println(listResult2)
  println(listResult3)
}


