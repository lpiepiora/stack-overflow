import sbtassembly._
import java.io.File
import sbtassembly.Plugin.MergeStrategy

class IncludeFromJar(val jarName: String) extends MergeStrategy {

  val name = "includeFromJar"

  def apply(tmp: File, path: String, files: Seq[File]): Either[String, Seq[(File, String)]] = {
    val includedFiles = files.flatMap { f =>
      val (source, _, _, isFromJar) = sbtassembly.AssemblyUtils.sourceOfFileForMerge(tmp, f)
      if (isFromJar && source.getName == jarName) Some(f -> path) else None
    }
    Right(includedFiles)
  }

}
