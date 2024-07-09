package part5_validated

object TestRegistrationDataEither extends App {
  val anish: Either[DomainValidation, RegistrationData] = FormValidator.validateForm(
    username = "username",
    password = "Password@123",
    firstName = "anish",
    lastName = "pandey",
    age = 25
  )

  val res1 = anish match {
    case Left(error) => error.errorMessage
    case Right(value) => s"${value.username} - ${value.password} - ${value.firstName} - ${value.lastName} - ${value.age}"
  }
  // Password must be at least 10 characters long,
  // including an uppercase and a lowercase letter,
  // one number and one special character.
  println(res1)
}
