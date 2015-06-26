# sbt-sonarrunner-plugin
An SBT plugin to publish code quality data to SonarQube

Build status
------------

![Build health](https://travis-ci.org/aol/sbt-sonarrunner-plugin.svg)


Installation
------------

Add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.aol.sbt" % "sbt-sonarrunner-plugin" % "1.0.0")
```

Configuration
-------------

To use specific Sonar settings, add the following to your `build.sbt` file:

```scala
sonarProperties := Seq(
      "sonar.host.url" -> "http://sonarhostname.com",
      "sonar.jdbc.username" -> "sonar",
      "sonar.jdbc.password" -> "sonar",
      "sonar.coverage.exclusions" -> "**/MobileAppController.java,**/LegacyArticleController.java"
    )
```

Full list of Sonar analysis parameters
--------------------------------------
http://docs.sonarqube.org/display/SONAR/Analysis+Parameters
