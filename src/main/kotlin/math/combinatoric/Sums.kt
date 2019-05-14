package math.combinatoric

import extensions.isAscending
import util.astTrue
import util.range
import java.util.*

// returns sorted sequence of sums of two not neccessarily distinct elements from this array list
// elements in array list must be distinct, triples of (indexA, indexB, desiredSum) are returned
fun ArrayList<Double>.traverseSumsOfTwoElementsInOrder(ascending: Boolean = true): Sequence<Triple<Int, Int, Double>> {
	if (this.size < 2)
		return emptySequence()
	astTrue(this.isAscending())
	astTrue(this.toHashSet().size == size, "elements must be distinct")

	val t = TreeMap<Double, Pair<Int, Int>>({ a, b -> if ((a > b) xor ascending) -1 else 1 })
	for (i in range(this)) {
		if (ascending)
			t[this[i] + this[i]] = Pair(i, i)
		else
			t[this[i] + this[size - 1]] = Pair(i, size - 1)
	}
	return generateSequence {
		if (t.isEmpty())
			return@generateSequence null
		val (sum, pair) = t.pollFirstEntry()
		val (a, b) = pair
		if (ascending) {
			if (b < size - 1)
				t[this[a] + this[b + 1]] = Pair(a, b + 1)
		} else {
			if (b > a)
				t[this[a] + this[b - 1]] = Pair(a, b - 1)
		}
		return@generateSequence Triple(a, b, sum)
	}
}

fun createSumCombinations(sum: Int,
                          startSeq: Sequence<ArrayList<Int>> = sequenceOf(ArrayList()))
		: Sequence<ArrayList<Int>> {
	astTrue(sum >= 0)
	if (sum == 0)
		return startSeq

	return (sum downTo 1).asSequence().flatMap { num ->
		createSumCombinations(sum - num, startSeq.map {
			val al = ArrayList(it)
			al.add(num)
			return@map al
		})
	}
}