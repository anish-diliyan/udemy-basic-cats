package part1_type_classes.example

import part1_type_classes.example.TypeClass.CanBePetSyntax.CanBePetOps

object TypeClass {
  // lets assume you want to add speak method for Dog and cat, and you can not update the
  // source code of Dog and Cat, because it id defined by other(assuming).
  // We can solve this by using type class

  // Step1: Define the type class as generic trait
  // generic because same can be used for multiple types, here for Cat and Dog both
  trait CanBePet[A]{
    def pet(a: A): Unit
  }

  // Step2: Type class instance, The second step of the process is to create instances of the
  // type class for the data types you want to enhance.

  // implement the pet method as desired for the Dog type.
  implicit val dogCanBePet: CanBePet[Dog] = new CanBePet[Dog] {
    override def pet(a: Dog): Unit = println(s"I am a Dog = ${a.name}, I am a pet Animal")
  }
  // implement the pet method as desired for the Cat type.
  implicit val catCanBePet: CanBePet[Cat] = new CanBePet[Cat] {
    override def pet(a: Cat): Unit = println(s"I am a Cat = ${a.name}, I am a pet Animal")
  }
  // implement the pet method as desired for the Lion type.
  implicit val lionCanBePet: CanBePet[Lion] = new CanBePet[Lion] {
    override def pet(a: Lion): Unit = println(s"I am a Lion = ${a.name}, I am not a pet Animal")
  }

  // I tag the instance as implicit so it can be easily pulled into the code that
  // I’ll write in the next steps.

  // Step 3: The API (interface)
  // Create the functions that you want consumers of your API to see.
  // There are two possible approaches in this step:

  // Step 3a: The Interface Objects approach, pass type class instance as an implicit
  // and call method of type class using the implicit instance
  def petAnimal[A](animal: A)(implicit canBePet: CanBePet[A]): Unit = {
    canBePet.pet(animal)
  }

  // Step 3b: Add method to the Type class
  object CanBePetSyntax {
    implicit class CanBePetOps[A](value: A) {
      def petAnimal(implicit canBePet: CanBePet[A]): Unit = {
        canBePet.pet(value)
      }
    }
  }


  def main(args: Array[String]): Unit = {
    val duggu = Dog("duggu")
    petAnimal(duggu)

    val myCat = Cat("myCat")
    myCat.petAnimal
  }
}
