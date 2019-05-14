package datastructures

// greater value is better
class BestFinder<K, V : Comparable<V>>() {

	private var bestKey: K? = null
	private var bestValue: V? = null

	val key: K
		get() = bestKey!!
	val value: V
		get() = bestValue!!

	fun update(newKey: K, newValue: V) {
		if (bestValue == null || newValue.compareTo(bestValue!!) > 0) {
			bestValue = newValue
			bestKey = newKey
		}
	}

	override fun toString(): String {
		return "best value with key $bestKey: $bestValue"
	}
}