package math.sequences

class InfSequence<A>(
		private var currentValue: A,
		private val func: (Long, A) -> A
) : Iterator<A> {

	private var index: Long = 0L

	override fun next(): A {
		currentValue = func(index, currentValue)
		index++
		return currentValue
	}

	override fun hasNext(): Boolean = true

}