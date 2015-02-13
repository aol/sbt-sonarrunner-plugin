sbtPlugin := true

name := "sbt-sonarruner"

organization := "com.aol"

libraryDependencies += "org.codehaus.sonar.runner" % "sonar-runner-dist" % "2.3"

scalaVersion := "2.10.4"
  
version := "git describe --tags --dirty --always".!!.trim

