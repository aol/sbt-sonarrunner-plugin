sbtPlugin := true

name := "sbt-sonarrunner-plugin"

organization := "com.aol.sbt"

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-encoding", "UTF-8"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.3" % "provided")

scriptedLaunchOpts := {
  scriptedLaunchOpts.value ++
    Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}

scriptedBufferLog := false

libraryDependencies += "org.codehaus.sonar.runner" % "sonar-runner-dist" % "2.4"

import scala.sys.process._
version := "git describe --tags --dirty --always".!!.stripPrefix("v").trim

publishMavenStyle := false

bintrayOrganization := Some("aol")

bintrayPackageLabels := Seq("sbt", "sonar", "sbt-native-packager")

bintrayRepository := "scala"

licenses +=("MIT", url("http://opensource.org/licenses/MIT"))

resourceGenerators in Compile += resourceManaged in Compile map { dir =>
  val file = dir / "latest-version.properties"
  val contents = "name=%s\nversion=%s".format(name, version)
  IO.write(file, contents)
  Seq(file)
}