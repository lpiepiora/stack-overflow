import AssemblyKeys._

assemblySettings

mergeStrategy in assembly := {
  case PathList("file.txt") => new IncludeFromJar("first.jar")
  case x => (mergeStrategy in assembly).value(x)
}

