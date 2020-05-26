package jp.co.dwango.s99

import jp.co.dwango.s99.binary_trees.Tree
import jp.co.dwango.s99.binary_trees.Node
import jp.co.dwango.s99.binary_trees.End
import scala.math.Ordering.Implicits._

object P57 {
  implicit class RichTree[T](self: Tree[T]) {
    def addValue[U >: T](
        newValue: U
    )(implicit view: T => Ordered[U]): Node[U] = {
      self match {
        case Node(value, left, right) =>
          if (newValue > value) Node(value, left, right.addValue(newValue))
          else if (newValue < value)
            Node(value, left.addValue(newValue), right)
          else Node(value, left, right)
        case End => Node(newValue, End, End)
      }
    }
  }
  object Tree {
    def fromList[T](
        elements: List[T]
    )(implicit view: T => Ordered[T]): Tree[T] = {
      elements.foldLeft[Tree[T]](End) { (tree, t) => tree.addValue(t) }
    }
  }
}
