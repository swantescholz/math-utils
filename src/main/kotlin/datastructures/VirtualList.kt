package datastructures

class VirtualList<T>(val entryFunction: (Int) -> T, val offset: Int = 0, override val size: Int = Int.MAX_VALUE) : List<T> {
	override fun containsAll(elements: Collection<T>): Boolean {
		throw UnsupportedOperationException()
	}

	override fun get(index: Int): T {
		return entryFunction(index + offset)
	}

	override fun indexOf(element: T): Int {
		throw UnsupportedOperationException()
	}

	override fun isEmpty(): Boolean {
		return size == 0
	}

	override fun iterator(): Iterator<T> {
		throw UnsupportedOperationException()
	}

	override fun lastIndexOf(element: T): Int {
		throw UnsupportedOperationException()
	}

	override fun listIterator(): ListIterator<T> {
		throw UnsupportedOperationException()
	}

	override fun listIterator(index: Int): ListIterator<T> {
		throw UnsupportedOperationException()
	}

	override fun subList(fromIndex: Int, toIndex: Int): List<T> {
		return VirtualList(entryFunction, fromIndex, toIndex - fromIndex)
	}

	override fun contains(element: T): Boolean {
		throw UnsupportedOperationException()
	}

}