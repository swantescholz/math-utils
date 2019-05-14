package math.factorization

import math.primes.isPrime
import java.util.*

private fun Long._r_createFactorizations(maxDivisor: Long): ArrayList<ArrayList<Long>> {
	if (this < 2)
		return arrayListOf(ArrayList())
	if (this.isPrime()) {
		if (this <= maxDivisor)
			return arrayListOf(arrayListOf(this))
		return ArrayList()
	}
	val al = ArrayList<ArrayList<Long>>()
	for (d in this.computeDivisors().asSequence().drop(1).takeWhile { it <= maxDivisor }) {
		val n = this / d
		for (factorization in n._r_createFactorizations(d)) {
			factorization.add(d)
			al.add(factorization)
		}
	}
	return al
}

fun Long.createFactorizations(): ArrayList<ArrayList<Long>> {
	if (this < 1)
		throw IllegalArgumentException("too low!")
	if (this == 1L)
		return arrayListOf(arrayListOf(this))
	return this._r_createFactorizations(Long.MAX_VALUE)
}

fun Long.countPrimeFactorOfFactorial(prime: Long): Long {
	var res = 0L
	var primePow = prime
	while (primePow <= this) {
		res += this / primePow
		primePow *= prime
	}
	return res
}