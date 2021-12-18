name := """accounts"""
organization := "br.ufpe.cin"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
scalaVersion := "2.13.7"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.3.3"
