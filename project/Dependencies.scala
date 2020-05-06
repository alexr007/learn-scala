import sbt._

object Dependencies {

  object Versions {
    val cats             = "2.1.1"
    val catsEffect       = "2.1.2"
    val catsMeowMtl      = "0.4.0"
    val catsRetry        = "1.1.0"
    val circe            = "0.13.0"
    val ciris            = "1.0.4"
    val javaxCrypto      = "1.0.1"
    val fs2              = "2.3.0"
    val http4s           = "0.21.4"
    val http4sJwtAuth    = "0.0.4"
    val log4cats         = "1.0.1"
    val newtype          = "0.4.3"
    val refined           = "0.9.13"
    val redis4cats       = "0.9.6"
    val skunk            = "0.0.7"
    val squants          = "1.6.0"
    val slick            = "3.3.2"
    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.11.0"
    val logback          = "1.2.3"
    val scalaCheck       = "1.14.3"
    val scalaTest        = "3.1.1"
    val scalaTestPlus    = "3.1.1.1"
    val zio              = "1.0.0-RC18-2"
    val sqlPg            = "42.2.10"
    val sqlH2            = "1.4.200"
    val silencer         = "1.6.0"
    val scalaz           = "7.2.30"
  }

  object Libraries {
    def circe(artifact: String): ModuleID  = "io.circe"   %% artifact % Versions.circe
    def ciris(artifact: String): ModuleID  = "is.cir"     %% artifact % Versions.ciris
    def http4s(artifact: String): ModuleID = "org.http4s" %% artifact % Versions.http4s
    def zio(artifact: String): ModuleID    = "dev.zio"    %% artifact % Versions.zio
    def scalaz(artifact: String): ModuleID = "org.scalaz" %% artifact % Versions.scalaz
    def slick(artifact: String): ModuleID  = "com.typesafe.slick" %% artifact % Versions.slick
    def fs2(artifact: String): ModuleID    = "co.fs2"     %% artifact % Versions.fs2

    val cats          = "org.typelevel"    %% "cats-core"     % Versions.cats
    val catsMeowMtl   = "com.olegpy"       %% "meow-mtl-core" % Versions.catsMeowMtl
    val catsEffect    = "org.typelevel"    %% "cats-effect"   % Versions.catsEffect
    val catsRetry     = "com.github.cb372" %% "cats-retry"    % Versions.catsRetry
    val squants       = "org.typelevel"    %% "squants"       % Versions.squants

    val fs2core       = fs2("fs2-core")
    val fs2reactive   = fs2("fs2-reactive-streams")

    val scalazCore    = scalaz("scalaz-core")
    val scalazEffect  = scalaz("scalaz-effect")

    val circeCore     = circe("circe-core")
    val circeGeneric  = circe("circe-generic")
    val circeParser   = circe("circe-parser")
    val circeRefined   = circe("circe-refined")

    val cirisCore     = ciris("ciris")
    val cirisEnum     = ciris("ciris-enumeratum")
    val cirisRefined   = ciris("ciris-refined")

    val http4sServer  = http4s("http4s-blaze-server")
    val http4sDsl     = http4s("http4s-dsl")
    val http4sClient  = http4s("http4s-blaze-client")
    val http4sCirce   = http4s("http4s-circe")

    val zioCore       = zio("zio")
    val zioStreams    = zio("zio-streams")

    val slickCore     = slick("slick")
    val slickHikari   = slick("slick-hikaricp")

    val sqlPg         = "org.postgresql"      % "postgresql"       % Versions.sqlPg
    val sqlH2         = "com.h2database"      % "h2"               % Versions.sqlH2

    val http4sJwtAuth = "dev.profunktor"      %% "http4s-jwt-auth" % Versions.http4sJwtAuth

    val refinedCore    = "eu.timepit"          %% "refined"          % Versions.refined
    val refinedCats    = "eu.timepit"          %% "refined-cats"     % Versions.refined

    val log4cats      = "io.chrisdavenport"   %% "log4cats-slf4j"  % Versions.log4cats
    val newtype       = "io.estatico"         %% "newtype"         % Versions.newtype

    val javaxCrypto   = "javax.xml.crypto"    % "jsr105-api"       % Versions.javaxCrypto

    val skunkCore     = "org.tpolecat"        %% "skunk-core"      % Versions.skunk
    val skunkCirce    = "org.tpolecat"        %% "skunk-circe"     % Versions.skunk

    val redis4catsEffects  = "dev.profunktor" %% "redis4cats-effects"  % Versions.redis4cats
    val redis4catsLog4cats = "dev.profunktor" %% "redis4cats-log4cats" % Versions.redis4cats

    // Compiler plugins
    val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
    val kindProjector    = "org.typelevel" % "kind-projector"      % Versions.kindProjector

    // Runtime
    val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
    val scalaCheck    = "org.scalacheck"    %% "scalacheck"      % Versions.scalaCheck
    val scalaTest     = "org.scalatest"     %% "scalatest"       % Versions.scalaTest
    val scalactic     = "org.scalactic"     %% "scalactic"       % Versions.scalaTest
    val scalaTestPlus = "org.scalatestplus" %% "scalacheck-1-14" % Versions.scalaTestPlus

    val silencesAnnotation = "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full
    // https://index.scala-lang.org/ghik/silencer/silencer-plugin/1.4.2?target=_2.13
    val silencesCompilerPlugin = compilerPlugin("com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full)
  }

}
