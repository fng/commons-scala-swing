import sbt._
import Keys._

object build extends Build {
	
	val commonsWicketScala = Project(
		id = "commons-scala-swing",
		base = file("."),
		settings = Defaults.defaultSettings ++ Seq[Project.Setting[_]](
	      organization := "com.github.fng",
	      version := "1.0.0-SNAPSHOT",
	      scalaVersion := "2.9.1",
	      crossPaths := false
	    ) ++ Seq(
		      libraryDependencies ++= Seq(ScalaSwing)
		    )
	)
	
	lazy val ScalaSwing = "org.scala-lang" % "scala-swing" % "2.9.1" withSources
}