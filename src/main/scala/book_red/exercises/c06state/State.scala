package book_red.exercises.c06state

import scala.annotation.tailrec

trait RNG {
  def nextInt: (Int, RNG) // minimal behavior: nextInt + return new seed
}

object RNGApp extends App {

  case class SimpleRand(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
      val nextRNG = SimpleRand(newSeed) // The next state, which is an `RNG` instance created from the new seed.
      val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
      (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
    }
  }

  val sr0: SimpleRand = SimpleRand(0) // initialize random
  val (r1, sr1):(Int, RNG) = sr0.nextInt // chain them
  val (r2, sr2)            = sr1.nextInt // and we will get always same values
  val (r3, sr3)            = sr2.nextInt // predictability !
  println(r1)
  println(r2)
  println(r3)
  println("------")

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (a, r) =  rng.nextInt
    val b = math.abs(a)
    (b, r)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (a, r) =  rng.nextInt
    val d = a.toDouble
    (d, r)
  }

  def intDouble(rng: RNG): ((Int,Double), RNG) = {
    val (a1, r1) = rng.nextInt
    val (a2, r2) = r1.nextInt
    ((a1, a2.toDouble), r2)
  }

  def doubleInt(rng: RNG): ((Double,Int), RNG) = {
    val (a, r) = rng.nextInt
    val (a2, r2) = r.nextInt
    val d2 = a2.toDouble
    ((d2, a), r2)
  }

  def double3(rng: RNG): ((Double,Double,Double), RNG) = {
    val (a1, r1) = rng.nextInt
    val (a2, r2) = r1.nextInt
    val (a3, r3) = r2.nextInt
    ((a1.toDouble, a2.toDouble, a3.toDouble), r3)
  }

  def mk1(r: RNG): (Int, RNG) = {
    val (rv, rg) = nonNegativeInt(r)
    (rv % 10, rg)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    @tailrec
    def go(cnt: Int, acc: List[Int], r: RNG): (List[Int], RNG) = cnt match {
      case 0 => (acc, r)
      case _ => mk1(r) match {
        case (rv, rg) => go(cnt - 1, rv :: acc, rg)
      }
    }
    val (list, rx) = go(count, Nil, rng)
    (list reverse, rx)
  }

  private val seed = SimpleRand(7)
  val r10 = ints(5)(seed)
  println(r10)

  // this is definition of function which transform s1 to s2
  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = s => s.nextInt

  // doesn't change the state, just provides value. (lift ?)
  def unit[A](a: A): Rand[A] = s => (a, s)

  def map[A,B](sf: Rand[A])(f: A => B): Rand[B] = s => {
    val (a: A, r: RNG) = sf(s)
    val b: B = f(a)
    (b, r)
  }

  // nonNegativeInt: RNG => (Int, RNG)
  //                    Rand[Int]
  val nonNegativeInt3: Rand[Int] = nonNegativeInt

  def nonNegativeInt2(rng: RNG): Rand[Int] =
    map(_ => rng.nextInt)(i => math.abs(i))

  def nonNegativeEven: Rand[Int] =
    map(nonNegativeInt3)(i => i - i % 2)




  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = ???

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = ???

  def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] = ???
}

case class State[S,+A](run: S => (A, S)) {
  def map[B](f: A => B): State[S, B] =
    ???
  def map2[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
    ???
  def flatMap[B](f: A => State[S, B]): State[S, B] =
    ???
}

sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)

object State {
  type Rand[A] = State[RNG, A]
  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = ???
}
