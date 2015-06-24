package com.aol.sbt.sonar

import java.io.File

import org.sonar.runner.Main
import sbt.Keys._
import sbt._

object SonarRunnerPlugin extends AutoPlugin {

  object autoImport {
    val sonarProperties = settingKey[Seq[(String, String)]]("SonarRunner configuration properties. See http://docs.codehaus.org/display/SONAR/Analysis+Parameters.")
    val sonar = taskKey[Unit]("Runs Sonar agent")
    val generateSonarConfiguration = taskKey[File]("Generates Sonar configuration")
  }

  import com.aol.sbt.sonar.SonarRunnerPlugin.autoImport._

  override def trigger = allRequirements

  def runSonarAgent(configFile: File) = {
    println("**********************************")
    println("Publishing reports to SonarQube...")
    println("**********************************")
    Main.main(Array[String]("-D", "project.settings=" + configFile.getCanonicalPath, "-D", "project.home=" + file(".").absolutePath))
  }

  override def projectSettings: Seq[Setting[_]] = Seq(
    generateSonarConfiguration := makeConfiguration(target.value + "/sonar-project.properties", sonarProperties.value),
    sonarProperties := Seq(
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
      "sonar.jdbc.password" -> "sonar"
    ),
    sonar := runSonarAgent(generateSonarConfiguration.value)
  )

  private[this] def filePathsToString(files: Seq[File]) = files.filter(_.exists).map(_.getAbsolutePath).toSet.mkString(",")

  private[this] def makeConfiguration(configPath: String, props: Seq[(String, String)]): File = {
    val propertiesAsString = (props).toSeq.map { case (k, v) => "%s=%s".format(k, v)}.mkString("\n")
    val propertiesFile = file(configPath)
    IO.write(propertiesFile, propertiesAsString)
    propertiesFile
  }
}