import sbt.complete._

import complete.DefaultParsers._

lazy val myRunTask = inputKey[Unit]("Runs actual tests")

lazy val FullQualifiedClassName = 
  (charClass(c => isScalaIDChar(c) || (c == '.'), "class name")).+.string

def commaDelimited(display: String) = 
  token(Space) ~> repsep(token(FullQualifiedClassName, display), token(","))

lazy val testClassArgs: Parser[Seq[String]] = 
  commaDelimited("<full qualified test class name>").map { 
    xs: Seq[String] => xs.map(e => s" -s $e ") 
  }

myRunTask := Def.inputTaskDyn {
  val classes = testClassArgs.parsed
  runMainInCompile(classes)
}.evaluated

def runMainInCompile(classes: Seq[String]) = Def.taskDyn {
  (runMain in Compile).toTask(s" it.paperdragon.stackoverflow.Runner -P1 -C reporter.TestReporter -o ${classes.mkString}")
}

