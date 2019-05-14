package datastructures

import java.util.*

class Ring<T>(override val size: Int, initFunction: (Int) -> T) : List<T> {

	private val data = ArrayList<T>()
	private var nextIndex = 0

	init {
		for (i in 0..size - 1)
			data.add(initFunction(i))
	}

	fun add(element: T) {
		data[nextIndex] = element
		nextIndex = (nextIndex + 1) % size
	}

	override fun containsAll(elements: Collection<T>): Boolean = data.containsAll(elements)

	operator fun set(index: Int, value: T) {
		data[(size + nextIndex + index) % size] = value
	}

	override operator fun get(index: Int): T {
		return data[(size + nextIndex + index) % size]
	}

	override fun indexOf(element: T): Int = throw UnsupportedOperationException()

	override fun isEmpty(): Boolean = false

	class MyIterator<T>(val ring: Ring<T>) : Iterator<T> {
		var currentIndex = ring.nextIndex
		var done = false
		override fun next(): T {
			val res = ring.data[currentIndex]
			currentIndex = (currentIndex + 1) % ring.size
			if (currentIndex == ring.nextIndex)
				done = true
			return res
		}

		override fun hasNext(): Boolean {
			return !done
		}

	}

	// iterates over the last size elements (past to present)
	override fun iterator(): Iterator<T> = MyIterator(this)

	override fun lastIndexOf(element: T): Int = throw UnsupportedOperationException()

	override fun listIterator(): ListIterator<T> = throw UnsupportedOperationException()

	override fun listIterator(index: Int): ListIterator<T> = throw UnsupportedOperationException()

	override fun subList(fromIndex: Int, toIndex: Int): List<T> = throw UnsupportedOperationException()

	override fun contains(element: T): Boolean = data.contains(element)

	fun asSequence(): Sequence<T> = this.iterator().asSequence()

	override fun toString(): String {
		return asSequence().joinToString(prefix = "[", postfix = "]") { it.toString() }
	}
}