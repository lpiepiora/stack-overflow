import sbt._
import Keys._
import classpath._

object MyPlugin extends Plugin {

  val createClass = taskKey[Unit]("Creates new instance of a class")

  lazy val myPluginSettings = inConfig(Compile)(Seq(
    createClass := {
      val analysis = (compile in Compile).value
      val loader = ClasspathUtilities.toLoader((classDirectory in Compile).value ** "*", scalaInstance.value.loader)
      val myClass = Class.forName("com.stackoverflow.lpiepiora.MyClass", true, loader).newInstance
      println(myClass.asInstanceOf[{def helloWorld(): String}].helloWorld())
    }
  ))

}

