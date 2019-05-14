package datastructures

import java.util.*

//matrix should be ascending in x and y direction, drops duplicates
class SortedMatrixTraversion<T : Comparable<T>>(val maxValue: T, val minX: Int = 0, val minY: Int = 0,
                                                val matrixFunction: (Int, Int) -> T) : Iterable<T> {

	override fun iterator(): Iterator<T> {
		return generateSequence {
			if (isEmpty())
				return@generateSequence null
			return@generateSequence pollFront()
		}.iterator()
	}

	data class V<U : Comparable<U>>(val x: Int, val y: Int, val value: U) : Comparable<V<U>> {
		override fun compareTo(other: V<U>): Int {
			if (value < other.value)
				return -1
			if (value > other.value)
				return 1
			if (x < other.x)
				return -1
			if (x > other.x)
				return 1
			if (y < other.y)
				return -1
			if (y > other.y)
				return 1
			return 0
		}

	}

	private fun createV(x: Int, y: Int) = V(x, y, matrixFunction(x, y))
	val entries = TreeSet<V<T>>()
	private var currentMaxX = minX

	init {
		entries.add(createV(minX, minY))
	}

	private fun add(x: Int, y: Int) {
		val newV = createV(x, y)
		if (newV.value <= maxValue)
			entries.add(newV)
	}

	private fun _pollFront(): T {
		val v = entries.pollFirst()
		if (v.x == currentMaxX) {
			currentMaxX++
			add(v.x + 1, minY)
		}
		add(v.x, v.y + 1)
		return v.value
	}

	fun pollFront(): T {
		val n = _pollFront()
		while (!isEmpty() && front() == n) {
			_pollFront()
		}
		return n
	}

	fun front() = entries.first().value
	fun isEmpty() = entries.isEmpty()
}