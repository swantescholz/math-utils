package datastructures

import extensions.max
import extensions.min
import java.util.*

class SortedPairHashSet<K : Comparable<K>>() : HashSet<Pair<K, K>>() {

	private fun computePair(a: K, b: K) = Pair(a.min(b), a.max(b))

	fun contains(a: K, b: K): Boolean {
		return super.contains(computePair(a, b))
	}

	operator fun get(a: K, b: K): Boolean {
		return this.contains(a, b)
	}

	fun add(a: K, b: K) {
		super.add(computePair(a, b))
	}
}