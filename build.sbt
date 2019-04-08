name := "Data_Test"

version := "0.1"

scalaVersion := "2.12.8"
val sparkVersion = "2.4.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "com.typesafe" % "config" % "1.2.1"
)