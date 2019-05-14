package datastructures

import java.util.*

class DisjointSet<K>() : Set<K> {
	private val nodes = HashMap<K, DisjointSetNode<K>>()

	override val size: Int
		get() = nodes.size

	override fun contains(element: K) = element in nodes

	override fun containsAll(elements: Collection<K>) = nodes.keys.containsAll(elements)

	override fun isEmpty() = nodes.isEmpty()

	override fun iterator(): Iterator<K> = nodes.keys.iterator()

	fun add(key: K) {
		if (key in nodes)
			throw IllegalArgumentException("key ${key} already exists")
		nodes[key] = DisjointSetNode(key)
	}

	fun areInSameSet(key1: K, key2: K): Boolean {
		checkKeyExists(key1)
		checkKeyExists(key2)
		return nodes[key1]!!.isInSameSet(nodes[key2]!!)
	}

	fun findRepresentativeOf(key: K): K {
		checkKeyExists(key)
		return nodes[key]!!.findRoot().key
	}

	fun getSetSizeFor(key: K): Int {
		checkKeyExists(key)
		return nodes[key]!!.findRoot().setSize
	}

	private fun checkKeyExists(key: K) {
		if (key !in nodes)
			throw IllegalArgumentException("key ${key} does not exists")
	}

	fun merge(key1: K, key2: K) {
		checkKeyExists(key1)
		checkKeyExists(key2)
		nodes[key1]!!.merge(nodes[key2]!!)
	}

	private class DisjointSetNode<K>(val key: K) {

		private var parent = this
		private var rank = 0
		private var treeSize = 1

		val setSize: Int
			get() = findRoot().treeSize

		fun isInSameSet(you: DisjointSetNode<K>): Boolean {
			return findRoot() === you.findRoot()
		}

		fun findRoot(): DisjointSetNode<K> {
			if (parent != this)
				parent = parent.findRoot()
			return parent
		}

		fun merge(you: DisjointSetNode<K>) {
			val xRoot = this.findRoot()
			val yRoot = you.findRoot()
			if (xRoot === yRoot)
				return
			// x and y are not already in same set. Merge them.
			if (xRoot.rank < yRoot.rank) {
				xRoot.parent = yRoot
				yRoot.treeSize += xRoot.treeSize
			} else {
				yRoot.parent = xRoot
				xRoot.treeSize += yRoot.treeSize
				if (xRoot.rank == yRoot.rank) {
					xRoot.rank++
				}
			}
		}
	}
}







