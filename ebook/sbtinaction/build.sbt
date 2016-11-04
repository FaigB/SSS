name := "preowned-kittens"

version in ThisBuild := "1.0"

organization in ThisBuild := "com.preownedkittens"

resolvers += "Preowned Kitten Maven Repostory" at "http://internal-repo.preowned-kittens.com"

//Custom keys for this build

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")

val makeVersionProperties = taskKey[Seq[File]]("Makes version.properties file.")

//Common settings/definitions for this build

def PreownedKittenProject(name: String): Project = {
	Project(name, file(name))
	.settings( Defaults.itSettings : _* )
	.settings(
    libraryDependencies += "org.specs2" %% "specs2" % "1.14" % "test",
    javacOptions in Compile ++= Seq("-target", "1.8", "-source", "1.8"),
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "teamon.eu Repo" at "http://repo.teamon.eu/",
      "Maven Repository" at "http://mvnrepository.com/artifact"
    )
  )
	.configs(IntegrationTest)	
}

gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lines.head

//Projects in this build

lazy val common = (
	PreownedKittenProject("common").
	settings(
		makeVersionProperties := {
			val propFile = new File((resourceManaged in Compile).value, "version.properties")
			val content = "version=%s" format (gitHeadCommitSha.value)
			IO.write(propFile,content)
			Seq(propFile)
		}
	)
)

lazy val analytics = (
	PreownedKittenProject("analytics").
	dependsOn(common).
	settings()
)

lazy val website = (
	PreownedKittenProject("website").
	dependsOn(common,analytics).
	settings()
)