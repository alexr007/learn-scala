package shapelessx.deserial.str

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import shapeless.Generic
import shapelessx.deserial.str.StringToCase.Reader

class StringToCaseSpec extends AnyFunSpec with Matchers {

  describe("deserialization String delimited with '|' to a case class") {

    case class ExcelLine(a: String, b: String, c: Boolean, d: Option[Double])
    object ExcelLine {
      implicit val reader = Reader.pickAndRead(Generic[ExcelLine])
    }

    it("should deserialize") {

      Reader.read[ExcelLine]("a|bb|true|3.14")
        .fold(_ => ???, identity) shouldEqual
        ExcelLine("a", "bb", true, Some(value = 3.14))

      Reader.read[ExcelLine]("a|bb|false|")
        .fold(_ => ???, identity) shouldEqual
        ExcelLine("a", "bb", false, None)
      
    }
  }
  
}
