import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.lbert",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "zio-redis",
    libraryDependencies ++= Seq(
      zio,
      scalaTest % Test,
      scalaCheck % Test
    ),
    scalacOptions ++= Seq(
      "-encoding", "utf8", // Option and arguments on same line
      "-Xfatal-warnings",  // New lines for each options
      "-deprecation",
      "-unchecked",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:postfixOps"
    ),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
  ).dependsOn(scredis)

lazy val scredis = (project in file("scredis")).
  settings(
    inThisBuild(List(
      organization := "io.lbert",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scredis",
    libraryDependencies ++= Seq(
      "com.github.scredis" %% "scredis" % "2.2.4"
    ),
    scalacOptions ++= Seq(
      "-encoding", "utf8", // Option and arguments on same line
      "-Xfatal-warnings",  // New lines for each options
      "-deprecation",
      "-unchecked",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:postfixOps"
    )
  )