version := "1.2.3"

name := "test-project"

organization := "aol"

val root = (project in file("."))
  .enablePlugins(SonarRunnerPlugin)


