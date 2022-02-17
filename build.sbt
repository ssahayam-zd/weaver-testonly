name := "weaver-resource-cleanup"

organization := "acme"

version := "0.0.1"

scalaVersion := "2.13.8"

fork := true

Test / parallelExecution := true

libraryDependencies ++= Seq(
  "io.monix" %% "monix" % "3.4.0", 
  "com.disneystreaming" %% "weaver-monix" % "0.6.10" % "test"
)

lazy val commonCompilerOptions =
  Seq(
      "-unchecked",
      "-encoding", "UTF-8",
      "-deprecation",
      "-feature"
    )

scalacOptions ++=
  commonCompilerOptions ++
  Seq(
      "-Werror"
  )

testFrameworks ++= Seq(
  new TestFramework("weaver.framework.Monix")
)  

scalacOptions in (Compile, console) := commonCompilerOptions

scalacOptions in (Test, console) := commonCompilerOptions

