package geeksforgeeks.p1basic

import tools.fmt.Fmt.{printArray, printBoolMatrix, printIndices, printLine, rightPad}

/**
  * https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
  * Given a set of non-negative integers, and a value sum,
  * determine if there is a subset of the given set with sum equal to given sum
  *
  * NP-Complete:
  * https://en.wikipedia.org/wiki/NP-completeness
  *
  */
/**
  * plain recursion (math)
  * exponential
  */
object B011SubsetSum {

  def subsetSum(s: Set[Int], sum: Int): Boolean = {
    if (sum == 0) return true
    if (sum < 0) return false
    if (s.isEmpty) return false

    val x = s.head

    subsetSum(s - x, sum) ||
    subsetSum(s - x, sum - x)
  }
}

object B011SubsetSumDP {

  def subsetSum(s0: Set[Int], sum: Int): Boolean = {
    val sa = s0.toArray.sorted
    val dp = Array.ofDim[Boolean](sum + 1, sa.length + 1) // by default all are false
    (0 to sa.length).foreach(dp(0)(_) = true)             // base case 1: if sum = 0 => true

    (1 to sum).foreach { psum =>                          // outer iteration by partial sum 1...sum
      sa.indices.foreach { j =>                           // inner iteration by set items
        val elem = sa(j)
        
        if (sum == elem) dp(psum)(j+1) = true             // base case 2: if sum = s(i) => true
        else             dp(psum)(j+1) = 
          dp(psum)(j) ||                                  // try to pull TRUE from the left 
            (psum >= elem && {
              val pSumWoElem = psum - elem 
              dp(pSumWoElem)(j)                           // try to pull TRUE from the prev solution
            })          
      }
    }
    printIndices(sa.indices, 3, "index:     ")
    printArray(sa, 3, "value:   ")
    printLine(30, '-')

    printIndices(dp(0).indices, 3, "dp indx:")
    printLine(30)
    var v = -1
    printBoolMatrix(dp, linePrefix = () => { v+=1; s"ss=${rightPad(v, 2)} " }, width = 3)

//    printBoolMatrix(dp)
    dp(sum)(sa.length)
  }
}
