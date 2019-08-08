package _value_types

object App007 extends App {
  case class PersonInt(x: Int) extends AnyVal
  case class BookInt(x: Int) extends AnyVal

  val i11 = PersonInt(1)
  val i12 = PersonInt(1)
  val i2 = BookInt(1)
  if (i11 == i12) println("Ok")


}
