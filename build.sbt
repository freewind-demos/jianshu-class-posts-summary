name := "jianshu-class-posts-summary"

version := "1.0"

organization := "org.my"

scalaVersion := "2.11.0"

sbtVersion := "0.13.5"

resolvers ++= Seq(
  "aliyun" at "http://maven.aliyun.com/nexus/content/groups/public/",
  "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "net.ruippeixotog" %% "scala-scraper" % "1.1.0"
)
