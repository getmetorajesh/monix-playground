import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "monix-playground",
    libraryDependencies ++= Seq(
    	scalaTest % Test,
    	"io.monix" %% "monix" % "3.0.0-RC1"
    	)
  )
