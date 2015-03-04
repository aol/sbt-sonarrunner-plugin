import bintray.Keys._

sbtPlugin := true

name := "sonarrunner-sbt-plugin"

organization := "com.aol"

libraryDependencies += "org.codehaus.sonar.runner" % "sonar-runner-dist" % "2.3"

scalaVersion := "2.10.3"
  
version := "git describe --tags --dirty --always".!!.trim


publishMavenStyle := false

bintrayPublishSettings

repository in bintray := "sbt-plugins"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization in bintray := None