version := "1.2.3"

name := "sonar-runner-sbt-plugin-test-project"

organization := "aol"

val root = (project in file("."))
  .enablePlugins(SonarRunnerPlugin).settings(
    sonarProperties := Seq(
      "sonar.host.url" -> "http://testurl.com",
      "sonar.jdbc.username" -> "sonar",
      "sonar.coverage.exclusions" -> "**/MobileAppController.java,**/LegacyArticleController.java"
    )
  )




