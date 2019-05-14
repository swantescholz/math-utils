package datastructures

//not tested!!!
class BigSet<T> protected constructor(val data: Array<T>) : Collection<T> {
	override fun containsAll(elements: Collection<T>): Boolean {
		throw UnsupportedOperationException()
	}

	override fun contains(element: T): Boolean {
		throw UnsupportedOperationException()
	}

	operator fun get(index: Int): T {
		return data[index]
	}

	override fun isEmpty(): Boolean {
		return size == 0
	}

	private class MyIterator<T>(val bigSet: BigSet<T>) : Iterator<T> {
		var currentIndex = 0

		override fun next(): T {
			return bigSet.data[currentIndex++]
		}

		override fun hasNext(): Boolean {
			return currentIndex < bigSet.size
		}

	}

	fun removeAt(index: Int) {
		if (index < 0 || index >= size)
			throw IndexOutOfBoundsException()
		data[index] = data[size - 1]
		size--
	}

	fun removeAllAt(indices: Set<Int>) {
		for (index in indices) {
			while (size - 1 in indices) {
				size--
			}
			if (index < size) {
				removeAt(index)
			}
		}
	}

	fun add(element: T) {
		if (size >= capacity)
			throw IndexOutOfBoundsException()
		data[size] = element
		size++
	}

	override fun iterator(): Iterator<T> {
		return MyIterator(this)
	}

	override var size: Int = 0
	val capacity: Int
		get() = data.size


	companion object {
		inline fun <reified T> create(capacity: Int, noinline initFunction: (Int) -> T): BigSet<T> {
			return BigSet(Array(capacity, initFunction))
		}
	}
}
