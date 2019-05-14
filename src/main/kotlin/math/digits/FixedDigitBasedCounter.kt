package math.digits

import extensions.sequences.lproduct
import java.util.*

// call increment first, before accessing
class FixedDigitBasedCounter(val length: Int, digitSizeFunction: (Int) -> (Int)) {
	private val maxs = Array(length, digitSizeFunction)
	val digits = ArrayList<Int>()
	private var doneRounds = -1

	init {
		digits.addAll(maxs.map { it - 1 })
	}

	override fun toString(): String {
		return digits.toString()
	}

	// initilialized to max value (max[n]-1,...), call once for 0000....
	fun increment() {
		var index = 0
		while (true) {
			if (index >= length) {
				doneRounds++
				break
			}
			digits[index]++
			if (digits[index] < maxs[index]) {
				break
			}
			digits[index] = 0
			index++
		}
	}

	fun decrement() {
		var index = 0
		while (true) {
			if (index >= length)
				break
			digits[index]--
			if (digits[index] >= 0) {
				break
			}
			digits[index] = maxs[index] - 1
			index++
		}
	}

	fun countPossibleNumbers(): Long {
		return maxs.asSequence().lproduct()
	}

	fun done(): Boolean {
		return doneRounds > 0
	}
}