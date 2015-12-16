version := "1.2.3"

name := "sonar-runner-sbt-plugin-test-project"

organization := "aol"

val root = (project in file("."))
  .enablePlugins(SonarRunnerPlugin).settings(
    sonarRunnerOptions := Seq("-v"),
    sonarProperties := Map(
      "sonar.host.url" -> "http://testurl.com",
      "sonar.jdbc.username" -> "sonar",
      "sonar.test.windows.path" -> """c:\test1\path1\file1""",
      "sonar.test.windows.path2" -> """c:/test2/path2/file2""",
      "sonar.coverage.exclusions" -> "**/MobileAppController.java,**/LegacyArticleController.java"
    )
  )




