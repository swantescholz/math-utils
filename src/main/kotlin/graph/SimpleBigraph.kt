package graph

import extensions.sequences.ass
import java.util.*


class SimpleBigraph<V>() : LinkedHashMap<V, LinkedHashSet<V>>() {

	val nodeCount: Int
		get() = size

	var edgeCount = 0
		private set(value) {
			field = value
		}

	val nodes: MutableSet<V>
		get() = keys

	fun addEdge(a: V, b: V) {
		putIfAbsent(a, LinkedHashSet())
		putIfAbsent(b, LinkedHashSet())
		this[a]?.add(b)
		this[b]?.add(a)
		edgeCount++
	}


	fun neighborsOf(node: V): LinkedHashSet<V> {
		return this[node] ?: LinkedHashSet()
	}


	fun hasEdge(a: V, b: V): Boolean {
		return b in (this[a] ?: return false)
	}

	override fun toString(): String {
		val es = LinkedHashSet<Pair<V, V>>()
		for (a in nodes) {
			for (b in neighborsOf(a)) {
				if (Pair(b, a) !in es)
					es.add(Pair(a, b))
			}
		}
		return "(${es.ass().map { "${it.first}-${it.second}" }.joinToString()})"
	}

}