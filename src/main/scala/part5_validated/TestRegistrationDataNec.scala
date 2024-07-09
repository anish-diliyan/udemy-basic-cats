package part5_validated

import cats.data.Validated
import cats.data.Validated.Valid
import part5_validated.FormValidatorNec.ValidationResult


object TestRegistrationDataNec extends App {
  val anish: ValidationResult[RegistrationData] = FormValidatorNec.validateForm(
    username = "username",
    password = "Password",
    firstName = "anish",
    lastName = "pandey",
    age = 25
  )

  val result = anish match {
    case Valid(data) =>
      s"${data.username} - ${data.password} - ${data.firstName} - ${data.lastName} - ${data.age}"
    case Validated.Invalid(e) => e.map(error => error.errorMessage)
  }
  println(result)
}
