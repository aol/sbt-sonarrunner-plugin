package com.aol.sbt.sonar

import java.io.{File, FileOutputStream}
import java.util.Properties

import org.sonar.runner.Main
import sbt.Keys._
import sbt._

import scala.collection.JavaConversions
;

object SonarRunnerPlugin extends AutoPlugin {

  object autoImport {
    val sonarProperties = settingKey[Map[String, String]]("SonarRunner configuration properties. See http://docs.codehaus.org/display/SONAR/Analysis+Parameters.")
    val sonar = taskKey[Unit]("Runs Sonar agent")
    val generateSonarConfiguration = taskKey[File]("Generates Sonar configuration")
    val sonarRunnerOptions = settingKey[Seq[String]]("Extra options for sonar runner")
  }

  import com.aol.sbt.sonar.SonarRunnerPlugin.autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    generateSonarConfiguration := makeConfiguration(target.value + "/sonar-project.properties", sonarProperties.value),
    sonarProperties := Map(
      "sonar.projectName" -> name.value,
      "sonar.projectVersion" -> version.value,
      "sonar.projectKey" -> "%s:%s".format(organization.value, name.value),
      "sonar.binaries" -> filePathsToString(Seq((classDirectory in Compile).value)),
      "sonar.sources" -> filePathsToString((unmanagedSourceDirectories in Compile).value),
      "sonar.tests" -> filePathsToString((unmanagedSourceDirectories in Test).value),
      "sonar.projectBaseDir" -> file(".").absolutePath,
      "sonar.sourceEncoding" -> "UTF-8",
      "sonar.host.url" -> "http://localhost:9000",
      "sonar.jdbc.url" -> "jdbc:mysql://localhost:3306/sonar",
      "sonar.jdbc.username" -> "sonar",
      "sonar.jdbc.password" -> "sonar"
    ),
    sonarRunnerOptions := Seq.empty,
    sonar := {
      lazy val logger: TaskStreams = streams.value
      runSonarAgent(generateSonarConfiguration.value, logger, sonarRunnerOptions.value)
    }
  )

  def runSonarAgent(configFile: File, logger: TaskStreams, sonarRunnerOptions: Seq[String]) = {
    logger.log.info("**********************************")
    logger.log.info("Publishing reports to SonarQube...")
    logger.log.info("**********************************")
    Main.main(Array[String]("-D", "project.settings=" + configFile.getCanonicalPath, "-D", "project.home=" + file(".").absolutePath) ++ sonarRunnerOptions)
  }

  private[this] def filePathsToString(files: Seq[File]) = files.filter(_.exists).map(_.getAbsolutePath).toSet.mkString(",")

  private[this] def makeConfiguration(configPath: String, props: Map[String, String]): File = {
    val p = new Properties()
    p.putAll(JavaConversions.mapAsJavaMap(props))
    p.store(new FileOutputStream(configPath), null)
    file(configPath)
  }
}