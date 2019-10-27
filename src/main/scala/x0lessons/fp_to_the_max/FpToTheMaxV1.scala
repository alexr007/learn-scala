package x0lessons.fp_to_the_max

import scala.io.StdIn.readLine
import scala.util.Random

object FpToTheMaxV1 extends App {
  // interaction representation
  def putStrLn(line: String): IO[Unit] = IO( () => println(line) )
  def getStrLn(): IO[String] = IO( () => readLine() )
  def nextInt(upper: Int): IO[Int] = IO(() => Random.nextInt(upper))

  def checkContinue(name: String): IO[Boolean] =
    for {
      _     <- putStrLn(s"Do you want to continue, $name?")
      input <- getStrLn().map(_.toLowerCase())
      cont  <- input match {
                  case "y" => IO.point(true)
                  case "n" => IO.point(false)
                  case _   => checkContinue(name)
               }
    } yield cont

  def gameLoop(name: String): IO[Unit] =
    for {
      num   <- nextInt(5).map(_ + 1)
      _     <- putStrLn(s"Dear, $name, gueass a number 1..5:")
      input <- getStrLn()
      _     <- parseInt(input).fold(
                 // empty case
                 putStrLn("You didn't enter a number")
               )(guess =>
                 // non-empty case
                 if (guess == num) putStrLn(s"You guessed right, $name!")
                 else putStrLn(s"You guessed wrong, $name, the number was:$num")
               )
      cont  <- checkContinue(name)
      _     <- if (cont) gameLoop(name) else IO.point(())

    } yield ()

  def main =
    for {
      _    <- putStrLn("What is your name?")
      name <- getStrLn()
      _    <- putStrLn(s"Hello, $name, welcome!")
      _    <- gameLoop(name)
    } yield ()

  main.core()
}
