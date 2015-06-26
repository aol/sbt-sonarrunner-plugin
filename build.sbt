sbtPlugin := true

name := "sbt-sonarrunner-plugin"

organization := "com.aol.sbt"

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-encoding", "UTF-8"
)

javaVersionPrefix in javaVersionCheck := Some("1.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.1" % "provided")

ScriptedPlugin.scriptedSettings

scriptedLaunchOpts := {
  scriptedLaunchOpts.value ++
    Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}

scriptedBufferLog := false

libraryDependencies += "org.codehaus.sonar.runner" % "sonar-runner-dist" % "2.4"

version := "git describe --tags --dirty --always".!!.stripPrefix("v").trim

publishMavenStyle := false

bintrayOrganization := Some("aol")

bintrayPackageLabels := Seq("sbt", "sonar", "sbt-native-packager")

bintrayRepository := "scala"

licenses +=("MIT", url("http://opensource.org/licenses/MIT"))