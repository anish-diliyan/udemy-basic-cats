// Step 1: define a type class Equal - cat already provide Equal so import this
import cats.Eq

// step 2: create instance of Equal for which you want to use, here we are importing int

// step 3: expose method to get instance of Equal

val intEquality = Eq[Int]

// use intEquality to call methods of Equal type class

println(intEquality.eqv(10, 20))

