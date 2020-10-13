package graphs.impls

import graphs.ops.LongestPath
import graphs.rep.DiGraph

/**
  * graph must be acyclic !!!
  * complexity: V x Max Chain length
  */
class LongestPathImpl(g: DiGraph) extends LongestPath {
  
  override def longestPath: Seq[Int] = {
    val from = Array.ofDim[Seq[Int]](g.v)

    def visitNext(vi: Int): List[Int] = g.adjTo(vi) match {
      case s if s.isEmpty => List(vi)
      case s              => vi :: s.map(visitNext).maxBy(_.length)
    }
    
    g.vertices.foreach(v => from(v) = visitNext(v))
//    from.indices.foreach(idx => println(s"$idx: ${from(idx)}"))
    from.maxBy(_.length)
  }
  
  def longestLength: Int = {
    val from = Array.ofDim[Int](g.v)

    def visitNext(vi: Int): Int = g.adjTo(vi) match {
      case s if s.isEmpty => 1
      case s              => 1 + s.map(visitNext).max
    }
    
    g.vertices.foreach(v => from(v) = visitNext(v))
    from.max
  }
  
}
