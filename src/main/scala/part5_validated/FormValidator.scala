package part5_validated

trait FormValidator {
  def validateUserName(userName: String): Either[DomainValidation, String] = {
    Either.cond(
      userName.matches("^[a-zA-Z0-9]+$"),
      userName,
      UsernameHasSpecialCharacters
    )
  }

  def validatePassword(password: String): Either[DomainValidation, String] = {
    Either.cond(
      password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"),
      password,
      PasswordDoesNotMeetCriteria
    )
  }

  def validateFirstName(firstName: String): Either[DomainValidation, String] = {
    Either.cond(
      firstName.matches("^[a-zA-Z]+$"),
      firstName,
      FirstNameHasSpecialCharacters
    )
  }

  def validateLastName(lastName: String): Either[DomainValidation, String] = {
    Either.cond(
      lastName.matches("^[a-zA-Z]+$"),
      lastName,
      LastNameHasSpecialCharacters
    )
  }

  def validateAge(age: Int): Either[DomainValidation, Int] = {
    Either.cond(
      age >= 18 && age <= 75,
      age,
      AgeIsInvalid
    )
  }

  def validateForm(
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    age: Int
  ): Either[DomainValidation, RegistrationData] = {
    //A for-comprehension is fail-fast. If some of the evaluations in the for block fails for some reason,
    // the yield statement will not complete.
    // In our case, if that happens we won't be getting the accumulated list of errors.
    for {
      validatedUserName <- validateUserName(username)
      validatedPassword <- validatePassword(password)
      validatedFirstName <- validateFirstName(firstName)
      validatedLastName <- validateLastName(lastName)
      validatedAge <- validateAge(age)
    } yield RegistrationData(
      validatedUserName,
      validatedPassword,
      validatedFirstName,
      validatedLastName,
      validatedAge
    )
  }

}

object FormValidator extends FormValidator
