package com.aol.sbt.plugins

import java.io.File

import org.sonar.runner.Main
import sbt.Keys._
import sbt._

object SonarRunnerPlugin extends AutoPlugin {

  object autoImport {
    val sonarProperties = SettingKey[Map[String, String]]("sonar-properties", "SonarRunner configuration properties. See http://docs.codehaus.org/display/SONAR/Analysis+Parameters.")
  }

  import com.aol.sbt.plugins.SonarRunnerPlugin.autoImport._

  override def trigger = allRequirements

  override def projectSettings: Seq[Setting[_]] = Seq(
    sonarRunner,
    sonarProperties := Map()
  )


  val sonarRunner = TaskKey[Unit]("sonar", "Generate configuration and start SonarRunner") := {
    def filePathsToString(files: Seq[File]) = files.filter(_.exists).map(_.getAbsolutePath).toSet.mkString(",")
    val defaults = Map(
      "sonar.projectName" -> name.value,
      "sonar.projectVersion" -> version.value,
      "sonar.projectKey" -> "%s:%s".format(organization.value, name.value),
      "sonar.binaries" -> filePathsToString(Seq((classDirectory in Compile).value)),
      "sonar.sourceEncoding" -> "UTF-8",
      "sonar.sources" -> filePathsToString((unmanagedSourceDirectories in Compile).value),
      "sonar.tests" -> filePathsToString((unmanagedSourceDirectories in Test).value),
      "sonar.host.url" -> "http://localhost:9000",
      "sonar.jdbc.username" -> "sonar",
      "sonar.projectBaseDir" -> file(".").absolutePath,
      "sonar.exclusions" -> "",
      "sonar.java.source" -> "8",
      "sonar.java.target" -> "8",
      "sonar.language" -> "java",
      "sonar.jdbc.url" -> "jdbc:mysql://localhost:3306/sonar",
      "sonar.jdbc.password" -> "sonar")
    val propertiesAsString = (defaults ++ sonarProperties.value).toSeq.map { case (k, v) => "%s=%s".format(k, v) }.mkString("\n")
    val propertiesFile = file(target.value + "/sonar-project.properties")

    IO.write(propertiesFile, propertiesAsString)
    println("**********************************")
    println("Publishing reports to SonarQube...")
    println("**********************************")
    Main.main(Array[String]("-D", "project.settings=" + propertiesFile.getCanonicalPath, "-D", "project.home=" + file(".").absolutePath))
  }
}