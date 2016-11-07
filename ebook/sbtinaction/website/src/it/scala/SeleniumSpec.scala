import org.scalatest.selenium.HtmlUnit
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, ShouldMatchers}

/**
  * Created by faig.babayev on 9/6/2016.
  */
class SeleniumSpec  extends FlatSpec with ShouldMatchers with BeforeAndAfter with BeforeAndAfterAll with HtmlUnit {
  val homePage : String = "http://localhost:9000"

  "Home page" should "redirect to kitten list" in {
    go to "http://localhost:9000"
    currentUrl should startWith ("http://localhost:9000/kittens")
  }

  it should "show three dropdown lists of attributes in sorted order" in {
    def select(name: String) = findAll(xpath("//select[@name='" + name + "']/option")).map { _.text }.toList
    def assertListCompleteAndIsSorted(list: Seq[String]) = {
      list.size should be(20)
      list.sorted should be(list)
    }

    go to homePage + "/kittens"
    assertListCompleteAndIsSorted(select("select1"))
    assertListCompleteAndIsSorted(select("select2"))
    assertListCompleteAndIsSorted(select("select3"))
  }
}
