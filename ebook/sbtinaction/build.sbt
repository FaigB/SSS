name := "preowned-kittens"

resolvers += "Preowned Kitten Maven Repostory" at "http://internal-repo.preowned-kittens.com"

//Custom keys for this build

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")

val makeVersionProperties = taskKey[Seq[File]]("Makes version.properties file.")

//Common settings/definitions for this build



def PreownedKittenProject(name: String): Project = {
	Project(name, file(name)).
	settings(
		organization := "com.preownedkittens",
		version := "1.0",
		libraryDependencies += "org.specs2" %% "specs2" % "1.14" % "test",
		libraryDependencies += "org.pegdown" % "pegdown" % "1.0.2" % "test",
		libraryDependencies += "junit" % "junit" % "4.11" % "test",
		libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
		)
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

fork in Test := true

javaOptions in Test += "-Dspecs2.outDir=" + (target.value/"generated/test-reports").getAbsolutePath

testOptions in Test += Tests.Argument("HTML")

lazy val analytics = (
	PreownedKittenProject("analytics").
	dependsOn(common).
	settings()
)

lazy val website = (
	PreownedKittenProject("website").
	dependsOn(common).
	settings()
)