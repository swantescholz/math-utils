package algorithms

import math.DEFAULT_EPSILON
import util.astGreaterEqual
import util.astUnequal

fun ClosedRange<Double>.binarySearchZero(isPositive: (Double) -> Boolean,
                                         epsilon: Double = DEFAULT_EPSILON): Double {
	var a = this.start
	var b = this.endInclusive
	val sa = isPositive(a)
	val sb = isPositive(b)
	astUnequal(sa, sb)
	while (true) {
		val m = (a + b) * .5
		if (m - a < epsilon)
			return m
		val sv = isPositive(m)
		if (sv == sa) {
			a = m
		} else {
			b = m
		}
	}
}

// returns the largest index before the sign is flipped
fun IntRange.binarySearchZero(isPositive: (Int) -> Boolean): Int {
	return (start.toLong()..endInclusive.toLong()).binarySearchZero { isPositive(it.toInt()) }.toInt()
}

// returns the largest index before the sign is flipped
inline fun LongRange.binarySearchZero(isPositive: (Long) -> Boolean): Long {
	var a = this.start
	var b = this.endInclusive
	val sa = isPositive(a)
	val sb = isPositive(b)
	if (sa == sb)
		return b
	while (true) {
		val m = (a + b) / 2
		if (m == a)
			return m
		val sv = isPositive(m)
		if (sv == sa) {
			a = m
		} else {
			b = m
		}
	}
}


// returns index of element with the largest index that is still okay, list should be "sorted": okay okay not-okay
inline fun <T> List<T>.binaryFindLargest(stillOkay: (T) -> Boolean): Int {
	if (this.size == 0)
		return -1
	if (!stillOkay(this.first()))
		return -1
	if (stillOkay(this.last()))
		return this.size - 1
	if (this.size == 1)
		return 0
	var low = 0
	var high = size - 1

	while (low + 1 < high) {
		val mid = (low + high).ushr(1) // safe from overflows
		val midVal = get(mid)
		if (stillOkay(midVal))
			low = mid
		else
			high = mid

	}
	return low
}

// quickly returns largest index that is still okay, search starts at 0
// if zero is not okay, it returns -1
inline fun exponentialFindLargest(stillOkay: (Long) -> Boolean): Long {
	if (!stillOkay(0))
		return -1
	var index1 = 0L
	var index2 = 1L
	while (stillOkay(index2)) {
		index1 = index2
		index2 *= 2
		astGreaterEqual(index2, 0L, "long overflow")
	}
	return (index1..index2 + 1).binarySearchZero(stillOkay)
}