package datastructures

import java.util.*

// saves for each key, only the "best" value
class BestMap<K, V>(val isFirstBetter: (V, V) -> Boolean) : HashMap<K, V>() {

	fun update(key: K, value: V) {
		if (containsKey(key)) {
			if (isFirstBetter(value, this[key]!!)) {
				put(key, value)
			}
		} else {
			put(key, value)
		}
	}
}