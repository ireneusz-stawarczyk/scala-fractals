name := "scala-fractals"

organization := "edu.scala.fractal"

version := "1.0-SNASPHOT"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-swing" % "2.10.3",
  "com.typesafe.akka" %% "akka-actor" % "2.2.4",
  "org.specs2" %% "specs2" % "2.3.10" % "test"
)