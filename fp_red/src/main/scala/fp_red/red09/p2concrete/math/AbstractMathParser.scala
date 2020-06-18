package fp_red.red09.p2concrete.math

import fp_red.red09.p1impl.Reference
import fp_red.red09.p1impl.ReferenceTypes.Parser
import fp_red.red09.p2concrete.math.BiTree._

trait AbstractMathParser[A] {
  val R = Reference
  import R._

  def process[EA >: Expr[A]](t: (EA, Seq[(Char, EA)])): EA = t match {
    case (n, Nil) => n
    case (a, l) => l.foldLeft(a) { case (acc, (op, x)) => mkNode(op, acc, x) }
  }

  val plusOrMinus: Parser[Char] = char('+') | char('-')
  val mulOrDiv: Parser[Char] = char('*') | char('/')

  def value: Parser[Expr[A]]

  /** recursive grammar */
  def parens = surround(char('('), char(')'))(addSub)
  def factor = value | parens

  def divMul = ( factor ** (mulOrDiv ** factor).many ).map(process)
  def addSub: Parser[Expr[A]] = ( divMul ** (plusOrMinus ** divMul).many ).map(process)

  /** root of grammar */
  def built = root(addSub)

}
