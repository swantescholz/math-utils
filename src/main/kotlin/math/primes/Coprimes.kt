package math.primes

import math.factorization.PrimeFactorization
import java.util.*

fun Long.computeLowerCoprimes(): ArrayList<Long> {
	val res = (1..this - 1).toSortedSet()
	for (p in PrimeFactorization.factorize(this).primeFactors) {
		if (p >= this)
			break
		for (n in 1..this) {
			val x = p * n
			if (x >= this)
				break
			res.remove(x)
		}
	}
	return res.toCollection(arrayListOf<Long>())
}