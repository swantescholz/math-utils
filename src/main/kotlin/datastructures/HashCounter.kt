package datastructures

import java.util.*

class HashCounter<T>(private val defaultNumber: Long = 0L) : HashMap<T, Long>() {

	fun copy(): HashCounter<T> {
		val res = HashCounter<T>(defaultNumber)
		for ((k, v) in this) {
			res[k] = v
		}
		return res
	}

	fun increase(key: T, stepSize: Long = 1L) {
		var item = get(key)
		val newCount = item + stepSize
		if (newCount == defaultNumber)
			remove(key)
		else
			put(key, newCount)
	}

	override fun get(key: T): Long {
		return super.get(key) ?: defaultNumber
	}
}