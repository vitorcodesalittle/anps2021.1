import play.sbt.PlayRunHook
import sbt._

import scala.sys.process.Process

object FrontendRunHook {
  def apply(base: File): PlayRunHook = {
    object UIBuildHook extends PlayRunHook {

      var process: Option[Process] = None
      val isWindows = System.getProperty("os.name").toLowerCase().contains("win")

      def prefixCommandIfWindows(cmd: String, prefix: String = "cmd /c"): String = if (isWindows) prefix + cmd else cmd

      val npmInstall: String = prefixCommandIfWindows(FrontendCommands.dependencyInstall)
      val npmBuildWatch: String = prefixCommandIfWindows(FrontendCommands.buildWatch)

      override def beforeStarted(): Unit = {
        if (!(base / "ui" / "node_modules").exists()) Process(npmInstall, base / "ui").!
      }

      override def afterStarted(): Unit = {
        process = Option(
          Process(npmBuildWatch, base / "ui").run
        )
      }

      override def afterStopped(): Unit = {
        process.foreach(_.destroy())
        process = None
      }
    }

    UIBuildHook
  }
}