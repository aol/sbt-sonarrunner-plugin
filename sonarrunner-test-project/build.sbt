version := "1.2.3"

name := "sonar-runner-sbt-plugin-test-project"

organization := "aol"

val root = (project in file("."))
  .enablePlugins(SonarRunnerPlugin)


