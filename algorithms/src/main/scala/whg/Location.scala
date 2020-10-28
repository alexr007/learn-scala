package whg

/**
  * represents location
  * @param x a-h -coordinate
  *          
  *          abcdefgh => 12345678
  * @param y 1-8 -coordinate
  *          
  *          12345678 => 12345678
  */
case class Loc private (x: Int, y: Int) {
  import Loc.{xToL, yToD}
  def isOnBoard = Loc.isOnBoard(x) && Loc.isOnBoard(y)
  def move(dx: Int, dy: Int) = Loc.move(this, dx, dy)
  override def toString: String = s"${xToL(x)}${yToD(y)}"
  /** syntax experiments, used only in tests */
  def >(dst: Loc) = Move(this, dst)
}

object Loc {
  def parse(loc: String) =
    Option(loc.trim)
      .filter(_.length == 2)
      .map(_.toCharArray)
      .map { case Array(x, y) => (iToX(x), iToY(y)) }
      .filter { case (x, y) => isOnBoard(x) && isOnBoard(y) }
      .map { case (x, y) => Loc(x, y) }
      .toRight(ImErrorParsingLocation(loc))
  def parseOrDie(loc: String) = parse(loc).fold(_.die, identity)
  def isOnBoard(a: Int) = a >= 1 && a <= 8
  def apply(loc: String): Loc = parseOrDie(loc)
  def xToL(i: Int) = (i + 'a' - 1).toChar // 1..8 => 'a'..'h'
  def yToD(i: Int) = (i + '0').toChar     // 1..8 => '1'..'8'
  def iToX(i: Int) = i - 'a' + 1          // 'a'..'h' => 1..8
  def iToY(i: Int) = i - '1' + 1          // '1'..'8' => 1..8
  /** automatically filter locations out of the board */
  def move(l: Loc, dx: Int, dy: Int) =
    Some(l)
      .map { case Loc(x, y) => Loc(x + dx, y + dy) }
      .filter(_.isOnBoard)
}

/** syntax experiments, used only in tests */
object L {
  def a(y: Int) = Loc(1, y)
  def b(y: Int) = Loc(2, y)
  def c(y: Int) = Loc(3, y)
  def d(y: Int) = Loc(4, y)
  def e(y: Int) = Loc(5, y)
  def f(y: Int) = Loc(6, y)
  def g(y: Int) = Loc(7, y)
  def h(y: Int) = Loc(8, y)
}

case class Move private (start: Loc, finish: Loc) {
  override def toString: String = s"$start=>$finish"
}

object Move {
  def parse(move: String) =
    Option(move.trim)
      .filter(_.length == 4)
      .map(_.splitAt(2))
      .toRight(ImErrorParsingMove(move))
      .flatMap { case (s1, s2) => for {
        x <- Loc.parse(s1)
        y <- Loc.parse(s2)
      } yield Move(x, y) }
    match { // we don't have Cats to use leftMap, bt this is only for tests
      case Right(m) => Right(m)
      case Left(_)  => Left(ImErrorParsingMove(move))
    }
  def parseOrDie(move: String) = parse(move).fold(_.die, identity)
  def apply(move: String): Move = parseOrDie(move)

  /** align implementation done to the interface given */
  private def xx(x: Int) = (x + 'a').toChar
  private def yy(y: Int) = (7 - y + '1').toChar
  /** array has length 4 because of underline given implementation */
  def fromArray(move: Array[Int]) = move match {
    case Array(w,x,y,z) => s"${xx(w)}${yy(x)}${xx(y)}${yy(z)}"
  }

}
