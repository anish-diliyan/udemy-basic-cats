package part2_basic_cats

import cats.Eq
import cats.syntax.eq._

object Ex2Equal extends App {
  case class Account(id: Long, number: String, balance: Double, owner: String)

  object Account {
    implicit val universalEq: Eq[Account] = Eq.fromUniversalEquals[Account]

    object Instances {
      implicit val byIdEq: Eq[Account] = Eq.instance[Account]{ (acc1, acc2) =>
        acc1.id === acc2.id
      }
      // Ex2: transform account in acc.id and
      // provide implicit Eq for Long type, because id is long
      // implicit def byIdEq2(implicit eqLong: Eq[Long]): Eq[Account] = Eq.by(acc => acc.id)
      implicit def byNumberEq(implicit eqString: Eq[String]): Eq[Account] = Eq.by(_.number)
    }

  }

  val acc1: Account = Account(1, "100-100", 200.0, "Anish")
  val acc2: Account = Account(2, "100-100", 200.0, "Manish")
  // it is using universal equality as implicit
  val result1: Boolean = Eq[Account].eqv(acc1, acc2)
  println("result 1 is : " + result1) // false

  import Account.Instances.byNumberEq
  val result2: Boolean = Eq[Account].eqv(acc1, acc2)
  println("result 2 is : " + result2) // true

  val result3: Boolean = acc1 == acc2
  println("result 3 is : " + result3) // true

}
