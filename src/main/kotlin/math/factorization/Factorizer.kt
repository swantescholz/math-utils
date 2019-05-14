package math.factorization

import datastructures.HashCounter
import extensions.square
import extensions.toal
import math.primes.PRIMES
import util.astFail
import util.astGreaterEqual
import util.astLessEqual
import java.util.*


class Factorizer(val maxValue: Int) {

	val smallestPrimeDivisors = createSmallestPrimeDivisorArray(maxValue, PRIMES)

	// potentially slow for numbers greater than maxValue
	fun factorize(n: Int): PrimeFactorization {
		astGreaterEqual(n, 1, "number must be positive")
		val hc = HashCounter<Long>()
		var x = n.toLong()
		if (x > maxValue) {
			for (p in PRIMES) {
				while (x % p == 0L) {
					hc.increase(p)
					x /= p
				}
				if (x <= maxValue)
					break
				if (p * p > x) {
					hc.increase(x)
					x = 1L
					break
				}
			}
			astLessEqual(x, maxValue.toLong())
		}
		var xi = x.toInt()
		while (xi != 1) {
			var spd = smallestPrimeDivisors[xi].toInt()
			if (spd == 0)
				spd = xi
			while (xi % spd == 0) {
				xi /= spd
				hc.increase(spd.toLong())
			}
		}
		val al = hc.map { Pair(it.key, it.value.toInt()) }.sortedBy { it.first }
		return PrimeFactorization(al.map { it.first }.toal(), al.map { it.second }.toal())
	}


	// returns an array with the smallest prime divisor for each number <= max (or 0 for primes)
	private fun createSmallestPrimeDivisorArray(max: Int, primes: ArrayList<Long> = PRIMES): Array<Short> {
		astLessEqual(max.toLong(), primes.last().square())
		val s0 = 0.toShort()
		val res = Array(max + 1, { s0 })
		for (p in primes) {
			if (p * p > max)
				return res
			for (index in p.toInt().square().toInt()..max step p.toInt()) {
				if (res[index] <= 0)
					res[index] = p.toShort()
			}
		}
		astFail("not enough primes to create array")
		null!!
	}
}