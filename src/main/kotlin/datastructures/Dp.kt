package datastructures

import java.util.*

abstract class DpHash<K, V>() {

	internal val data = HashMap<K, V>()

	abstract protected fun compute(k: K): V

	operator fun get(k: K): V {
		var value = data[k]
		if (value != null) {
			return value
		}
		value = compute(k)
		data[k] = value
		return value
	}

	infix operator fun contains(k: K): Boolean = k in data
}
