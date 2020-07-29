name := "backend-mock"

version := "0.0.2"

scalaVersion := "2.13.3"

lazy val `backendMock` = project in file(".")

val AkkaVersion = "2.6.8"
val AkkaHTTPVersion = "10.1.12"
val PlayJsonVersion = "2.9.0"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value / "scalapb"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http"   % AkkaHTTPVersion,

  "com.typesafe.play" %% "play-json" % PlayJsonVersion,

  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",

  "org.slf4j" % "slf4j-simple" % "1.7.30"
)
