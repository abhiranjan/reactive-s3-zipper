organization := "com.abhiranjan"

name := "reactive-s3-zipper"

version := "1.0"

scalaVersion := "2.12.8"

version := "0.0.4"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-core" % "3.6.11",
  "org.json4s" %% "json4s-jackson" % "3.6.11",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.4",
  "com.amazonaws" % "aws-java-sdk-bom" % "1.11.1034",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.1034",
  "commons-io" % "commons-io" % "2.6",
  "com.typesafe.akka" %% "akka-stream" % "2.5.32",
  "com.github.alexmojaki" % "s3-stream-upload" % "2.1.0",
  "com.lightbend.akka" %% "akka-stream-alpakka-s3" % "2.0.2"
)
