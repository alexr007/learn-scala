name := "learn-scala-cook-book-aa"

scalacOptions ++= Seq(
  "-language:postfixOps",
  "-feature",
  "-deprecation",
  "-unchecked",
//  "-Xfatal-warnings",
//  "-Ypartial-unification",
)
version := "0.2.5"
scalaVersion := "2.13.1"

resolvers ++= Seq(
//  Resolver.sonatypeRepo("releases"),
//  Resolver.sonatypeRepo("snapshots")
  //  "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
//  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

// https://www.scala-sbt.org/release/docs/Library-Dependencies.html
libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
  "com.typesafe.akka" %% "akka-actor" % "2.5.23",

  "com.typesafe.slick" %% "slick" % "3.3.2",          // core
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2", // connection pool
  "org.postgresql" % "postgresql" % "42.2.8",         // database driver

  "com.typesafe.play" %% "play-json" % "2.7.4",       // JSON converter
  "com.chuusai" %% "shapeless" % "2.3.3",

//  "com.h2database" % "h2" % "1.4.197",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe" % "config" % "1.4.0",

  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.scalatest" %% "scalatest" % "3.0.8",
)
