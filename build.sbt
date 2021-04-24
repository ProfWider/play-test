name := """play-app-test"""
organization := "com.arifwider"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)


scalaVersion := "2.13.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

herokuAppName in Compile := "desolate-meadow-36478"

herokuProcessTypes in Compile := Map(
  "web" -> "target/universal/stage/bin/play-app-test -Dhttp.port=$PORT",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.arifwider.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.arifwider.binders._"
