name := "eventhub-pub"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation")

libraryDependencies ++= Seq(
  "com.microsoft.azure" % "azure-eventhubs" % "0.13.1"
)