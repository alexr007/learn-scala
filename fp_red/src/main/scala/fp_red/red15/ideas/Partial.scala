package fp_red.red15.ideas

/**
  * A context in which exceptions can be caught and thrown 
  */
trait Partial[F[_]] {

  def attempt[A](a: F[A]): F[Either[Throwable, A]]

  def fail[A](t: Throwable): F[A]

}
