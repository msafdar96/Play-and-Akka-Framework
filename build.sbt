name := """FreeLancelot"""
organization := "com.project"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
lazy val akkaVersion = "2.6.18"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  guice, javaWs, ehcache, "org.mockito" % "mockito-core" % "4.4.0" % "test",
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test
)
