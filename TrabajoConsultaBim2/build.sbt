ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "TrabajoConsultaBim2",
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "3.4.0"
    )
  )
