name := "scala-manchester-finagle"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.twitter" %% "finagle-http" % "6.36.0"


lazy val finatraDemo = project.in(file("finatraDemo"))
lazy val finagleDemo = project.in(file("finagleDemo"))
