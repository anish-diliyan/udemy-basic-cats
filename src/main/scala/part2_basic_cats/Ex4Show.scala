package part2_basic_cats

import cats.Show
import cats.implicits.toShow

object Ex4Show extends App {
  case class Account(id: Long, number: String, balance: Double, owner: String)

  object Account {
    implicit val toStringShow: Show[Account] = Show.fromToString[Account]

    object Instances {
      implicit val byOwnerAnBalance: Show[Account] = Show.show[Account](acc =>
        s"${acc.owner} - $$${acc.balance}"
      )

      implicit  val prettyByOwner: Show[Account] = Show.show[Account](acc =>
        s"This account belong to ${acc.owner}"
      )
    }
  }

  val account  = Account(1, "100-100", 200.0, "Anish")
  println(account.show)

  import Account.Instances.prettyByOwner
  println(account.show)
}
