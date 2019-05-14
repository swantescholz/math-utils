package math.combinatoric

import extensions.*
import extensions.sequences.*
import math.BIG0
import math.digits.grayCodeBitFlips
import util.astGreaterEqual
import util.astLess
import util.astLessEqual
import util.astTrue
import java.math.BigInteger
import java.util.*

fun forEachBoundedSubsetProduct(collection: Collection<Long>, maxProduct: Long, consumer: (Long) -> Unit) {
	consumer(1L)
	val al = ArrayList(collection).sorted().toArrayList()
	fun fr(currentProduct: Long, currentIndex: Int) {
		try {
			if (currentIndex >= al.size)
				return
			val newProduct = Math.multiplyExact(currentProduct, al[currentIndex])
			if (newProduct > maxProduct)
				return
			fr(currentProduct, currentIndex + 1)
			if (newProduct <= maxProduct) {
				consumer(newProduct)
				fr(newProduct, currentIndex + 1)
			}
		} catch (e: ArithmeticException) {
		}
	}
	fr(1L, 0)
}

// returns all subsets (defined by bitset indices) that sum to the given desired sum
// numbers list must be sorted in ascending order, duplicates are okay
fun exactBigIntegerSubsetSum(numbersNonDecreasing: ArrayList<BigInteger>, desiredSum: BigInteger): ArrayList<BitSet> {
	0.seq(numbersNonDecreasing.size - 2).forEach {
		astLessEqual(numbersNonDecreasing[it], numbersNonDecreasing[it + 1], "numbers must be sorted in non-decreasing order!")
	}
	val size = numbersNonDecreasing.size
	val neccessaryTopNumbersCount = numbersNonDecreasing.ass().sum().let { maxSum ->
		for (i in size - 1 downTo 0) {
			if (maxSum - numbersNonDecreasing[i] >= desiredSum)
				return@let size - i - 1
		}
		return ArrayList()
	}
	val reducedSize = size - neccessaryTopNumbersCount
	val reducedSum = desiredSum - numbersNonDecreasing.takeLast(neccessaryTopNumbersCount).ass().sum()
	astLessEqual(reducedSize, 64, "too many numbers to handle the subset sum problem!")
	astGreaterEqual(numbersNonDecreasing[0], BIG0)
	fun createArrayOfAllSubsetSums(elements: ArrayList<BigInteger>): Array<Pair<BigInteger, Int>> {
		astLessEqual(elements.size, 31)
		astLessEqual(elements.size, 25) // otherwise too much space!
		val tp = 2.pow(elements.size).toInt()
		val res = Array(tp, { Pair(BIG0, 0) })
		var bitsAsInt = 0
		var currentSum = BIG0
		val bitFlips = grayCodeBitFlips(elements.size)
		for (bitFlip in bitFlips) {
			if (bitsAsInt.getBit(bitFlip)) {
				currentSum -= elements[bitFlip]
			} else {
				currentSum += elements[bitFlip]
			}
			bitsAsInt = bitsAsInt.setBit(bitFlip, !bitsAsInt.getBit(bitFlip))
			res[bitsAsInt] = Pair(currentSum, bitsAsInt)
		}
		res.sortBy { it.first }
		return res
	}

	val lefts = numbersNonDecreasing.withIndex().filter { it.index < reducedSize / 2 }.map { it.value }.toArrayList()
	val rights = numbersNonDecreasing.take(reducedSize).withIndex().filter { it.index >= reducedSize / 2 }.map { it.value }.toArrayList()
	val leftSums = createArrayOfAllSubsetSums(lefts)
	val rightSums = createArrayOfAllSubsetSums(rights)
	var a = 0
	var b = rightSums.size - 1
	val res = ArrayList<BitSet>()
	while (true) {
		val currentSum = leftSums[a].first + rightSums[b].first
		if (currentSum > reducedSum) {
			b--
			if (b < 0)
				break
		} else if (currentSum < reducedSum) {
			a++
			if (a >= leftSums.size)
				break
		} else {
			val aAtStartOfGoodRun = a
			while (leftSums[a].first + rightSums[b].first == reducedSum) {
				val bitSet = BitSet()
				for (i in size - neccessaryTopNumbersCount..size - 1) {
					bitSet.set(i)
				}
				for (bit in leftSums[a].second.seqSetBits()) {
					bitSet.set(bit)
				}
				for (bit in rightSums[b].second.seqSetBits()) {
					bitSet.set(lefts.size + bit)
				}
				res.add(bitSet)
				a++
				if (a >= leftSums.size)
					break
			}
			a = aAtStartOfGoodRun
			b--
			if (b < 0)
				break
		}
	}
	return res
}

// returns max subset sum <= max and the indices as bitset
fun maxBoundedSubsetSum(numbers: List<Double>, max: Double): Pair<Double, BitSet> {
	val size = numbers.size
	astLess(size, 65)
	val al = numbers.toArrayList()
	astGreaterEqual(al[0], 0.0)
	val lefts = al.withIndex().filter { it.index < size / 2 }.map { it.value }.toArrayList()
	val rights = al.withIndex().filter { it.index >= size / 2 }.map { it.value }.toArrayList()
	fun arraySubsets(elements: ArrayList<Double>): Array<Pair<Double, Int>> {
		astLessEqual(elements.size, 31)
		val tp = 2.pow(elements.size).toInt()
		val res = Array(tp, { Pair(0.0, 0) })
		for (i in 0..tp - 1) {
			var sum = 0.0
			for (bitIndex in i.seqSetBits()) {
				sum += elements[bitIndex]
			}
			res[i] = Pair(sum, i)
		}
		res.sortBy { it.first }
		return res
	}

	val leftSums = arraySubsets(lefts)
	val rightSums = arraySubsets(rights)
	var a = 0
	var b = rightSums.size - 1
	var (bestSum, bestA, bestB) = Triple(0.0, a, b)
	while (true) {
		val sum = leftSums[a].first + rightSums[b].first
		if (sum > max) {
			b--
			if (b < 0)
				break
		} else {
			if (sum > bestSum) {
				bestSum = sum
				bestA = a
				bestB = b
			}
			a++
			if (a >= leftSums.size)
				break
		}
	}
	val bitSet = BitSet()
	for (bit in leftSums[bestA].second.seqSetBits()) {
		bitSet.set(bit)
	}
	for (bit in rightSums[bestB].second.seqSetBits()) {
		bitSet.set(bit + lefts.size)
	}
	return Pair(bestSum, bitSet)
}

fun <T> ArrayList<T>.createSubsetsOfSize(subsetSize: Int): Sequence<ArrayList<T>> {
	if (subsetSize > this.size || subsetSize < 0)
		return emptySequence()
	if (subsetSize == 0)
		return sequenceOf(ArrayList())
	val indices = ArrayList<Int>(subsetSize)
	for (i in 0..subsetSize - 1) {
		indices.add(i)
	}
	var quit = false
	return generateSequence {
		if (quit)
			return@generateSequence null
		val res = indices.map { this[it] }.toArrayList()
		var index = subsetSize - 1
		while (indices[index] == this.size - (subsetSize - index)) {
			index--
			if (index < 0) {
				quit = true
				return@generateSequence res
			}
		}
		indices[index]++
		for (i in 1..subsetSize - 1 - index) {
			indices[index + i] = indices[index] + i
		}
		res
	}
}

fun ArrayList<Long>.seqSubsetProducts(subsetSize: Int, maxProduct: Long = Long.MAX_VALUE): Sequence<Long> {
	astTrue(this.isAscending())
	if (subsetSize > this.size || subsetSize == 0)
		return emptySequence()
	val indices = ArrayList<Int>(subsetSize)
	for (i in 0..subsetSize - 1) {
		indices.add(i)
	}

	val first = this.asSequence().take(subsetSize).product()
	if (first > maxProduct)
		return emptySequence()
	return generateSequence {
		var res = indices.map { this[it] }.product()
		do {
			var index = subsetSize - 1
			while (indices[index] == this.size - (subsetSize - index)) {
				index--
				if (index < 0) {
					return@generateSequence null
				}
			}
			res /= this[indices[index]]
			indices[index]++
			res *= this[indices[index]]
			for (i in 1..subsetSize - 1 - index) {
				res /= this[indices[index + i]]
				indices[index + i] = indices[index] + i
				res *= this[indices[index + i]]
			}
			if (index == 0 && res > maxProduct)
				return@generateSequence null
		} while (res > maxProduct)
		return@generateSequence res
	}.prepend(first)
}


