import bintray.Keys._

sbtPlugin := true

name := "sonarrunner-sbt-plugin"

organization := "com.aol"

libraryDependencies += "org.codehaus.sonar.runner" % "sonar-runner-dist" % "2.4"

scalaVersion := "2.10.3"

publishMavenStyle := false

bintrayPublishSettings

repository in bintray := "sbt-plugins"

licenses +=("MIT", url("http://opensource.org/licenses/MIT"))

bintrayOrganization in bintray := None

crossScalaVersions := Seq("2.10.3", "2.11.1")

releaseSettings

publishTo := {
  val nexus = "http://cmmaven.cm.aol.com:8081/nexus/content/repositories/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "aolcom-snaps")
  else
    Some("releases" at nexus + "aolcom")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")