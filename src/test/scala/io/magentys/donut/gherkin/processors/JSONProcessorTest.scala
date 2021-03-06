package io.magentys.donut.gherkin.processors

import java.io.{File, FileNotFoundException}
import io.magentys.donut.gherkin.model.StatusConfiguration
import org.scalatest.{FlatSpec, Matchers}

class JSONProcessorTest extends FlatSpec with Matchers {

  val rootDir = List("src", "test", "resources", "samples-1").mkString("", File.separator, File.separator)
  val statusConfiguration = StatusConfiguration(false, false, false, false)


  behavior of "JSONProcessor"

  it should "identify valid files in a directory" in {
    val jsonFiles = JSONProcessor.getValidFiles(new File(rootDir))
    jsonFiles.size shouldBe 10
    jsonFiles.contains(rootDir + "1.json") shouldBe true
    jsonFiles.contains(rootDir + "2.json") shouldBe true
    jsonFiles.contains(rootDir + "3.json") shouldBe true
    jsonFiles.contains(rootDir + "4.json") shouldBe true
    jsonFiles.contains(rootDir + "5.json") shouldBe true
    jsonFiles.contains(rootDir + "6.json") shouldBe true
    jsonFiles.contains(rootDir + "7.json") shouldBe true
    jsonFiles.contains(rootDir + "8.json") shouldBe true
    jsonFiles.contains(rootDir + "9.json") shouldBe true
    jsonFiles.contains(rootDir + "9a.json") shouldBe true
    jsonFiles.contains(rootDir + "empty_json.json") shouldBe false
    jsonFiles.contains(rootDir + "sample.xml") shouldBe false
  }

  it should "include json files only" in {
    val jsonFiles = JSONProcessor.getValidFiles(new File(rootDir))
    jsonFiles.map(name => name.endsWith(".json")).reduce(_ && _) shouldBe true
  }

  it should "exclude empty files" in {
    val jsonFiles = JSONProcessor.getValidFiles(new File(rootDir))
    jsonFiles.contains("empty_json.json") shouldBe false
  }

  it should "not parse incorrect file" in {
    intercept[FileNotFoundException] {
      val features = JSONProcessor.parseJsonFile(rootDir + "test.json")
      features shouldBe List.empty
    }
  }

  it should "handle all weirdos" in {
    val weirdos = JSONProcessor.loadFrom(new File("src/test/resources/samples-weirdos"))
    pending
  }


}
