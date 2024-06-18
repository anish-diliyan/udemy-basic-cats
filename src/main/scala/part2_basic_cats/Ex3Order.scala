package part2_basic_cats

import cats.Order


object Ex3Order extends App {
  case class Account(id: Long, number: String, balance: Double, owner: String)

  // order account by id, number, balance, owner

  object Account {
    implicit val ordById: Order[Account] = Order.from[Account]{(acc1, acc2) =>
      Order[Long].compare(acc1.id, acc2.id)
    }

    object Implicits {
      implicit def ordByNumber(implicit strOrder: Order[String]): Order[Account] =
        Order.by[Account, String](acc => acc.number)

      implicit def ordByBalance(implicit ordDouble: Order[Double]): Order[Account] =
        Order.from[Account]{ (acc1, acc2) =>
          ordDouble.compare(acc1.balance, acc2.balance)
        }

      implicit def ordByOwner(implicit strOrder: Order[String]): Order[Account] =
        Order.by[Account, String](acc => acc.owner)
    }

    def sort(accounts: List[Account])(implicit order: Order[Account]): List[Account] = {
      accounts.sorted(order.toOrdering)
    }
  }

  val acc1: Account = Account(2, "100-100", 200.0, "Anish")
  val acc2: Account = Account(1, "100-100", 300.0, "Manish")

  val sortByIdResult = Account.sort(List(acc1, acc2))
  println(sortByIdResult)

//  import Account.Implicits.ordByNumber
//  val sortByNumberResult = Account.sort(List(acc1, acc2))
//  println(sortByNumberResult)

//  import Account.Implicits.ordByBalance
//  val sortByBalanceResult = Account.sort(List(acc1, acc2))
//  println(sortByBalanceResult)

  import Account.Implicits.ordByOwner
  val sortByOwnerResult = Account.sort(List(acc1, acc2))
  println(sortByOwnerResult)
}
