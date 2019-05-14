package math.primes

import extensions.product
import java.util.*

fun computeRadicals(max: Int): ArrayList<Long> {
	val pfacs = Array(max + 1, { HashSet<Long>() })
	pfacs[0].add(1)
	pfacs[1].add(1)
	for (i in 2L..max) {
		var n = i
		for (p in PRIMES) {
			if (n % p == 0L) {
				pfacs[i.toInt()].add(p)
				if (p != n)
					pfacs[i.toInt()].addAll(pfacs[(n / p).toInt()])
				break
			}
		}
	}
	var rads = pfacs.withIndex().map { it.value.product() }.toCollection(arrayListOf<Long>())
	return rads
}
