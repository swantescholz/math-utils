package util

import java.util.*

// maps arbitray objects to successive indices (ints)
class IndexMapper<T>() {
	val values = ArrayList<T>()
	val indices = HashMap<T, Int>()
	val size: Int
		get() = values.size

	operator fun get(value: T): Int {
		if (indices.containsKey(value)) {
			return indices[value]!!
		}
		add(value)
		return values.size - 1
	}

	fun add(value: T) {
		if (indices.containsKey(value)) {
			return
		}
		val newIndex = values.size
		values.add(value)
		indices[value] = newIndex
	}

	fun getValueForIndex(index: Int): T {
		return values[index]
	}
}