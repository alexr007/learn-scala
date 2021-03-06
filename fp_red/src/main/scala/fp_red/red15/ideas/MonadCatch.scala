package fp_red.red15.ideas

import fp_red.red13.{Monad, Task}

/**
  * A context in which exceptions can be caught and thrown 
  */
trait MonadCatch[F[_]] extends Monad[F] with Partial[F]

object MonadCatch {

  implicit def task = new MonadCatch[Task] {
    def unit[A](a: => A): Task[A] = Task.unit(a)
    def flatMap[A, B](a: Task[A])(f: A => Task[B]): Task[B] = a flatMap f
    def attempt[A](a: Task[A]): Task[Either[Throwable, A]] = a.attempt
    def fail[A](err: Throwable): Task[A] = Task.fail(err)
  }

}
