val stage = taskKey[Unit]("Stage task")

val Stage = config("stage")

val root = project.in(file(".")).configs(Stage).settings( inConfig(Stage)(Classpaths.ivyBaseSettings): _* )

libraryDependencies in Stage := Seq("com.newrelic.agent.java" % "newrelic-api" % "3.7.0")

stage := {
  (update in Stage).value.allFiles.foreach { f =>
    IO.copyFile(f, baseDirectory.value / f.getName)
  }
}
