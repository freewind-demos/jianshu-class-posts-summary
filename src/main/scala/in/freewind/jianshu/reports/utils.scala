package in.freewind.jianshu.reports

import java.io.File

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils

object utils {
  def applyTemplate(content: String, targetFile: File): Unit = {
    val finalContent = StringUtils.replace(readTemplate(), "{{{table}}}", content)
    writeToFile(finalContent, targetFile)
    println("Wrote to file: " + targetFile)
  }

  private def readTemplate(): String = {
    val templateFile = new File("./src/main/resources/report-template.html")
    FileUtils.readFileToString(templateFile, "UTF-8")
  }

  private def writeToFile(content: String, targetFile: File) {
    FileUtils.writeStringToFile(targetFile, content, "UTF-8")
  }

}
