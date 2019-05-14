package algorithms

import util.astLess
import java.util.*

// returns offset indexed bitset of the values that pass
fun sieveInterval(min: Long, maxExcluding: Long, divisors: Sequence<Long>): BitSet {
	val size = (maxExcluding - min).toInt()
	astLess(size, 130000000)
	val res = BitSet(size)
	for (d in divisors) {
		var value = (min + d - 1) / d * d
		var index = value - min
		while (value < maxExcluding) {
			if (value != d)
				res.set(index.toInt())
			value += d
			index += d
		}
	}
	res.flip(0, size)
	return res
}