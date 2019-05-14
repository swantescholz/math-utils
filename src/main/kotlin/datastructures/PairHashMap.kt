package datastructures

import java.util.*

class PairHashMap<A, B, C>() : HashMap<Pair<A, B>, C>() {

	fun contains(a: A, b: B): Boolean {
		return this.contains(Pair(a, b))
	}

	fun put(a: A, b: B, c: C) {
		super.put(Pair(a, b), c)
	}

	operator fun get(a: A, b: B): C? {
		return super.get(Pair(a, b))
	}

	operator fun set(a: A, b: B, c: C) {
		put(a, b, c)
	}

	fun get(a: A, b: B, defaultValue: C): C {
		val tmp = super.get(Pair(a, b))
		if (tmp == null)
			return defaultValue
		return tmp
	}
}