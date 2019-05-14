package extensions

import java.util.*

infix operator fun BitSet.contains(n: Int): Boolean = this.get(n)

operator fun BitSet.iterator(): Iterator<Int> {
	class MyIterator(val bitSet: BitSet) : Iterator<Int> {
		var currentIndex = bitSet.nextSetBit(0)
		override fun next(): Int {
			val res = currentIndex
			if (currentIndex == Integer.MAX_VALUE)
				currentIndex = -1
			else
				currentIndex = bitSet.nextSetBit(currentIndex + 1)
			return res
		}

		override fun hasNext() = currentIndex >= 0
	}
	return MyIterator(this)
}

fun BitSet.asSequence(): Sequence<Int> = this.iterator().asSequence()
fun BitSet.ass() = asSequence()

fun BitSet.compareTo(o: BitSet): Int {
	val it1 = iterator()
	val it2 = o.iterator()
	while (it1.hasNext() && it2.hasNext()) {
		val diff = it1.next() - it2.next()
		if (diff < 0)
			return -1
		if (diff > 0)
			return 1
	}
	if (it1.hasNext())
		return 1
	if (it2.hasNext())
		return -1
	return 0
}