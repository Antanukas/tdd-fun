name := """tdd-fun"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,

  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.h2database" % "h2" % "1.4.193",
  "org.flywaydb" %% "flyway-play" % "3.0.1",

  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

