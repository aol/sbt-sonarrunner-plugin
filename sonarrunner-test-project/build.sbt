val root = (project in file("."))
  .enablePlugins(SonarRunnerPlugin)
  .settings(sonarProperties:= Map("a"->"b"))

