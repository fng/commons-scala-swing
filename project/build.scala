import sbt._
import Keys._

object build extends Build {

  val commonsWicketScala = Project(
    id = "commons-scala-swing",
    base = file("."),
    settings = Defaults.defaultSettings ++ Seq[Project.Setting[_]](
      organization := "com.github.fng",
      version := "0.3-SNAPSHOT",
      scalaVersion := "2.9.1",
      crossPaths := false,
      publishTo <<= (version) {
        (v: String) =>
          val repoSuffix = if (v.contains("-SNAPSHOT")) "snapshots" else "releases"
          val resolver = Resolver.file("gh-pages", new File("c:/code/fng.github.com/repo", repoSuffix))
          Some(resolver)
      }
    ) ++ Seq(
      libraryDependencies ++= Seq(ScalaSwing)
    )
  )

  lazy val ScalaSwing = "org.scala-lang" % "scala-swing" % "2.9.1" withSources
}