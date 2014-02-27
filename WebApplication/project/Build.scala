import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "PrivacyMatters"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
   "org.mongodb" % "mongo-java-driver" % "2.10.1",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
	   resolvers += Resolver.url("My GitHub Play Repository", url("http://alexanderjarvis.github.com/releases/"))(Resolver.ivyStylePatterns)
  )

}
