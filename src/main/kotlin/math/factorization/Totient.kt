package math.factorization

import extensions.floorSqrt
import extensions.ln
import extensions.pow
import math.fractions.LongFraction
import math.primes.PRIMES
import java.util.*


fun eulerTotientSum(maxInclusive: Long): Long {
	val n = maxInclusive
	val L = (n / (n.ln().ln())).pow(2.0 / 3).toInt()
	val sieve = LongArray(L + 1, { it.toLong() })
	val bigV = LongArray((n / L).toInt() + 1)
	for (p in 2..L) {
		if (p.toLong() == sieve[p]) {
			var k = p
			while (k <= L) {
				sieve[k] -= sieve[k] / p
				k += p
			}
		}
		sieve[p] += sieve[p - 1]
	}
	for (x in (n / L).toInt() downTo 1) {
		val k = n / x
		var res = k * (k + 1) / 2
		for (g in 2..k.floorSqrt().toInt()) {
			if (k / g <= L)
				res -= sieve[(k / g).toInt()]
			else
				res -= bigV[x * g]
		}
		for (z in 1..k.floorSqrt().toInt()) {
			if (z.toLong() != k / z)
				res -= (k / z - k / (z + 1)) * sieve[z]
		}
		bigV[x] = res
	}
	return bigV[1]
}

fun computeEulerTotients(limit: Int): ArrayList<Long> {
	val etfs = Array(limit, { LongFraction(1, 1) })
	for (p in PRIMES) {
		if (p >= limit)
			break
		for (i in p.toInt()..limit - 1 step p.toInt()) {
			etfs[i].n *= p
			etfs[i].d *= p - 1
		}
	}
	val ets = ArrayList<Long>(limit)
	ets.add(0)
	ets.add(0)
	for (i in 2..limit - 1) {
		ets.add(etfs[i].d * i / etfs[i].n)
	}
	return ets
}

fun Long.eulerTotient(): Long {
	if (this < 1)
		return 0
	if (this == 1L)
		return 1
	var et = 1L
	for ((p, n) in PrimeFactorization.factorize(this)) {
		et *= (p - 1) * p.pow(n - 1)
	}
	return et
}

fun Long.eulerTotientFraction(): LongFraction {
	if (this < 2)
		return LongFraction(0, 1)
	var n = 1L
	var d = 1L
	for ((p, _x) in PrimeFactorization.factorize(this)) {
		n *= p
		d *= p - 1
	}
	return LongFraction(n, d)
}