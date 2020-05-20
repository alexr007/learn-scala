package hackerrank.d200320_05.stones

import java.util

/**
  * https://www.hackerrank.com/challenges/manasa-and-stones/problem
  */
object ManasaAndStonesApp_Recursive extends App {

  def stones(n: Int, a: Int, b: Int) = {
    def fork(n: Int, acc: Set[Int]): Set[Int] = n match {
      case 0 => acc
      case _ => fork(n-1, acc flatMap { x => Set(x+a, x+b) })
    }
    fork(n-1, Set(0)).toArray.sorted
  }

  println(util.Arrays.toString(
    stones(4, 10, 100)
  ))
}
