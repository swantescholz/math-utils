package datastructures

import java.util.*

class Tree<T>(val element: T) {
	
	val isLeaf: Boolean
		get() = children.isEmpty()
	val size: Int
		get() = 1 + children.asSequence().map { it.size }.sum()
	val countChildren: Int
		get() = children.size
	
	private val children: ArrayList<Tree<T>> = ArrayList()
	
	operator fun get(childIndex: Int): Tree<T> = children[childIndex]
	
	fun traversePreOrder(function: (Tree<T>) -> Unit) {
		function(this)
		children.forEach { child -> child.traversePreOrder(function) }
	}
	
	fun traversePostOrder(function: (Tree<T>) -> Unit) {
		children.forEach { child -> child.traversePostOrder(function) }
		function(this)
	}
	
	fun hasChildWithElement(element: T): Boolean {
		return children.any { it.element == element }
	}
	
	fun findFirstChildWithElement(element: T): Tree<T>? {
		children.forEach { child ->
			if (child.element == element)
				return child
		}
		return null
	}
	
	fun addChild(child: Tree<T>) {
		children.add(child)
	}
	
	fun clearChildren() {
		children.clear()
	}
	
	// returns first found child, or null
	fun findChild(isChildDesired: (T) -> Boolean): Tree<T>? {
		children.forEach { child ->
			if (isChildDesired(child.element))
				return child
		}
		return null
	}
	
	
	fun reduceNonBranchingPathsToOneNode(): Tree<ArrayList<T>> {
		var subtree = this
		val elements = ArrayList<T>()
		while (true) {
			elements.add(subtree.element)
			if (subtree.isLeaf) {
				return Tree(elements)
			}
			if (subtree.countChildren > 1)
				break
			subtree = subtree[0]
		}
		val res = Tree(elements)
		subtree.children.forEach { child ->
			res.addChild(child.reduceNonBranchingPathsToOneNode())
		}
		return res
	}
	
}