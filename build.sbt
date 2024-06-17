version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.14"

lazy val root = (project in file(".")).settings(name := "udemy-basic-cats")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.3" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.12.0",
  "org.typelevel" %% "cats-laws" % "2.11.0",
  "org.typelevel" %% "discipline-core" % "1.7.0",
  "org.typelevel" %% "discipline-scalatest" % "2.2.0",
  "org.scalatest" %% "scalatest" % "3.2.18"
)

// https://github.com/leusgalvan/fp-course
