package type_classes.exercise

class PersonSpec extends MySpec {
  // TODO #17:
  //  Write tests for additional Eq instances defined in Person using
  //  Discipline and the 'checkAll' method
  checkAll("Eq[Person] by name", EqTests(Person.eqPersonName).eq)
  checkAll("Eq[Person] by id", EqTests(Person.eqPersonId).eq)
}
