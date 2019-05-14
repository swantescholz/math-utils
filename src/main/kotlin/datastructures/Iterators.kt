package datastructures

class SimpleIndexIterator<T>(val nextFunction: (Int) -> T,
                             val hasNextFunction: (Int) -> Boolean) : Iterator<T> {
	var currentIndex = 0

	override fun next(): T {
		return nextFunction(currentIndex++)
	}

	override fun hasNext(): Boolean {
		return hasNextFunction(currentIndex++)
	}
}