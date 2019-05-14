package datastructures

import util.astLess

data class LongArray3(val w: Int, val h: Int, val d: Int) {
	val array = LongArray(w * h * d)
	val size = w * h * d
	private val wh = w * h

	fun getIndex(x: Int, y: Int, z: Int) = x + y * w + z * wh

	operator fun get(x: Int, y: Int, z: Int): Long {
		val index = getIndex(x, y, z)
		astLess(index, size)
		return array[index]
	}

	operator fun set(x: Int, y: Int, z: Int, newValue: Long) {
		array[getIndex(x, y, z)] = newValue
	}
}