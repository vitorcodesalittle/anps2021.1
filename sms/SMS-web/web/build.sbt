name := """web"""
organization := "br.ufpe.cin"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += jdbc
// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.24"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0"
libraryDependencies += "com.github.jwt-scala" %% "jwt-play" % "9.0.2"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "br.ufpe.cin.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "br.ufpe.cin.binders._"
