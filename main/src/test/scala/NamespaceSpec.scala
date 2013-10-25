import org.parboiled2.TestParserSpec
import org.parboiled2.Core.Parser

class NamespaceSpec extends TestParserSpec {
  "The new parboiled parser" should {
    "be compilable being degined in another namespace and successfully recognize single char" in new TestParser0 {
      def targetRule = rule { 'x' }
      "x" must beMatched
    }
  }
}