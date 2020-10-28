package whg

sealed abstract class CFigure(val c: Color, private val s: Char) extends Product  {
  import Directions._

  def rep: Char = c match {
    case White => s.toUpper
    case Black => s.toLower
  }
  
  /** required move is a pawn and it's in forward direction */
  object IsPawnFwd {
    def unapply(as: (Move, Board)): Option[Loc] = as match { case (m, b) =>
      Some(m.finish)
        .filter(fi => mvPawnFwd(m.start, b).exists(_.contains(fi)))
    }
  }

  /** required move is a pawn and it's in bite (L or R) direction */
  object IsPawnBite {
    def unapply(as: (Move, Board)): Option[Loc] = as match { case (m, b) =>
      Some(m.finish)
        .filter(fi => mvPawnBite(m.start, b).exists(_.contains(fi)))
    }
  }
  /**
    * source color validation inside the Chess class
    * {{{nextFrom(l: Loc, b: Board): Seq[Seq[Length]]}}}
    * {{{isPathClean}}} checking path to the target
    */
  def validateMove(m: Move, b: Board) = {
    val colorOppositeMe = b.at(m.start).get.c.another
    def isOppositeAt(l: Loc) = b.isColorAt(l, colorOppositeMe)
    def isFreeAt(l: Loc) = b.isFreeAt(l)

    /**
      * available moves from current point
      * grouped by directions
      * for further checking whether path is empty
      */
    def nextFrom(l: Loc) = this match {
      case _: Pawn   => mvPawn(l, b)
      case _: Queen  => mvQueen(l)
      case _: King   => mvKing(l)
      case _: Bishop => mvBishop(l)
      case _: Knight => mvKnight(l)
      case _: Rook   => mvRook(l)
    }

    /** check the target for opposite color/empty */
    def checkTarget = this match {
      case _: Pawn => (m, b) match {
        case IsPawnFwd(fi)  => isFreeAt(fi)
        case IsPawnBite(fi) => isOppositeAt(fi)
        case _             => false
      }
      case _ => isFreeAt(m.finish) || isOppositeAt(m.finish)
    }

    /**
      * checks that all cells PRIOR to target are EMPTY
      * we don't need to consider special cases
      * for king/knight/pawn bite
      * since their sub-sequences will be empty
      */
    def checkPathClean(path: Seq[Loc]) = path.takeWhile(_ != m.finish).forall(isFreeAt)

    // start point validation is done in Chess class
    // TODO make all of them Either to distinguish these 3 different cases
    Some(m.finish)
      .flatMap(fi => nextFrom(m.start).find(_.contains(fi))) // vector with direction if found
      .filter(_ => checkTarget)                           // target is clean or opposite
      .filter(checkPathClean)                             // path is clean
      .map(_ => m)
      .toRight(ImInvalidFigureMove(m))
  }
}

final case class Pawn(color: Color) extends CFigure(color, 'p')   // Piece, Пешка
final case class Queen(color: Color) extends CFigure(color, 'q')
final case class King(color: Color) extends CFigure(color, 'k')
final case class Bishop(color: Color) extends CFigure(color, 'b') // Слон
final case class Knight(color: Color) extends CFigure(color, 'n') // Конь
final case class Rook(color: Color) extends CFigure(color, 'r')   // Ладья
