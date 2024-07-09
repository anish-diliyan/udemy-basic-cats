package part5_validated

import cats.data.ValidatedNec
import cats.implicits.{catsSyntaxTuple5Semigroupal, catsSyntaxValidatedIdBinCompat0}


sealed trait FormValidatorNec {
  /**
   * In this new implementation, we're using a NonEmptyChain, a data structure that guarantees that at
   * least one element will be present. In case that multiple errors arise,
   * you'll get a chain of DomainValidation.
   * ValidatedNec[DomainValidation, A] is an alias for Validated[NonEmptyChain[DomainValidation], A].
   * When you use ValidatedNec you're stating that your accumulative structure will be a NonEmptyChain.
   * With Validated, you have the choice about which data structure you want for reporting the errors.
   * We've declared the type alias ValidationResult that conveniently
   * expresses the return type of our validation.
   * validNec and invalidNec combinators lets you lift the success or failure in their respective container
   * either a Valid or Invalid of NonEmptyChain[A].
   * The applicative syntax (a, b, c, ...).mapN(...) provides us a way to
   * cumulatively apply the validation functions and yield a product with their
   * successful result or the accumulated errors in the NonEmptyChain.
   * Then, we transform that product with mapN into a valid instance of RegistrationData.
   */
  type ValidationResult[A] = ValidatedNec[DomainValidation, A]

  private def validateUserName(userName: String): ValidationResult[String] = {
    if (userName.matches("^[a-zA-Z0-9]+$")) userName.validNec
    else UsernameHasSpecialCharacters.invalidNec
  }

  private def validatePassword(password: String): ValidationResult[String] = {
    if (password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"))
      password.validNec
    else PasswordDoesNotMeetCriteria.invalidNec
  }

  private def validateFirstName(firstName: String): ValidationResult[String] = {
    if (firstName.matches("^[a-zA-Z]+$")) firstName.validNec
    else FirstNameHasSpecialCharacters.invalidNec
  }

  private def validateLastName(lastName: String): ValidationResult[String] = {
    if (lastName.matches("^[a-zA-Z]+$")) lastName.validNec
    else LastNameHasSpecialCharacters.invalidNec
  }

  private def validateAge(age: Int): ValidationResult[Int] = {
    if (age >= 18 && age <= 75) age.validNec
    else AgeIsInvalid.invalidNec
  }

  def validateForm(
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    age: Int
  ): ValidationResult[RegistrationData] = {
    (
      validateUserName(username),
      validatePassword(password),
      validateFirstName(firstName),
      validateLastName(lastName),
      validateAge(age)
    ).mapN(RegistrationData)
  }

}

object FormValidatorNec extends FormValidatorNec
