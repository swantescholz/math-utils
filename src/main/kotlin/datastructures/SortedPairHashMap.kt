package datastructures

import extensions.max
import extensions.min
import java.util.*

class SortedPairHashMap<K : Comparable<K>, V>() : HashMap<Pair<K, K>, V>() {

	private fun computePair(a: K, b: K) = Pair(a.min(b), a.max(b))

	fun contains(a: K, b: K): Boolean {
		return this.contains(computePair(a, b))
	}

	fun put(a: K, b: K, c: V) {
		super.put(computePair(a, b), c)
	}

	operator fun set(a: K, b: K, v: V) {
		super.put(computePair(a, b), v)
	}

	operator fun get(a: K, b: K): V? {
		return super.get(computePair(a, b))
	}

	fun get(a: K, b: K, defaultValue: V): V {
		val tmp = super.get(computePair(a, b))
		if (tmp == null)
			return defaultValue
		return tmp
	}
}