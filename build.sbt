name := "memcachedcloud-java-sample"

version := "1.0-SNAPSHOT"

resolvers += (
    "Spy Repository" at "http://files.couchbase.com/maven2/"
)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "spy" % "spymemcached" % "2.8.9"
)

play.Project.playJavaSettings
