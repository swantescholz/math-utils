package math.sequences

import util.range
import java.util.*

//returns start index, period length, repetition has to cover at least last half of list
fun <T> ArrayList<T>.findPeriodicRepetition(): Pair<Int, Int>? {
	val rev = this.asReversed()
	fun f(periodLen: Int): Boolean {
		for (start in range(periodLen)) {
			val want = rev[start]
			for (index in start + periodLen..size / 2 step periodLen) {
				if (rev[index] != want)
					return false
			}
		}
		return true
	}
	for (periodLen in 1..size / 2) {
		if (f(periodLen)) {
			var index = periodLen
			while (index < size && rev[index] == rev[index - periodLen])
				index++
			return Pair(size - index, periodLen)
		}
	}
	return null
}