package math.fractions

import math.BIG1
import math.BIG10
import math.primes.PRIMES
import java.math.BigInteger
import java.util.*

fun countReducedProperFractions(maxDenominator: Long, eulerTotients: ArrayList<Long>): Long {
	return eulerTotients.subList(2, maxDenominator.toInt() + 1).sum()
}


fun rationalEqual(numa: Int, dena: Int, numb: Int, denb: Int): Boolean {
	return numa * denb == dena * numb
}

fun cancelRational(numerator: Long, denominator: Long): Pair<Long, Long> {
	var n = numerator
	var d = denominator
	for (p in PRIMES) {
		while (n % p == 0L && d % p == 0L) {
			n /= p
			d /= p
		}
		if (p * p > n || p * p > d)
			break
	}
	return Pair(n, d)
}

fun getReciprocalCycleLength(divider: Long): Int {
	var d = divider
	while (d % 2 == 0L)
		d /= 2
	while (d % 5 == 0L)
		d /= 5
	if (d == 1L)
		return 0
	var tenPow = BigInteger.valueOf(10L)
	val bigD = BigInteger.valueOf(d)
	for (n in 1..2000) {
		if (tenPow.subtract(BIG1).mod(bigD).toInt() == 0)
			return n
		tenPow = tenPow.multiply(BIG10)
	}
	throw IllegalArgumentException("too hard divider")
}