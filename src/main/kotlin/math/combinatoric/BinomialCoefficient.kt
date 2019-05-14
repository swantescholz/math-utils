package math.combinatoric

import datastructures.ModLong
import extensions.min
import math.factorization.countPrimeFactorOfFactorial
import math.modular.modInv
import util.astLess
import util.extensions.big
import java.math.BigInteger

data class BinomialCoefficient(val n: Long, val k: Long) {

	private val r = k.min(n - k)

	fun compute(): BigInteger {
		if (k < 0 || k > n)
			return 0.big
		if (k == n || k == 0L)
			return 1.big
		var res = 1.big
		for (high in n downTo (n - r + 1)) {
			res *= high.big
		}
		for (low in 2L..r) {
			res /= low.big
		}
		return res
	}


	fun modPrime(prime: Long): Long {
		astLess(prime, Int.MAX_VALUE.toLong(), "prime too high, unsafe multiplications involved")
		if (n.countPrimeFactorOfFactorial(prime) > k.countPrimeFactorOfFactorial(prime) +
				(n - k).countPrimeFactorOfFactorial(prime))
			return 0L
		val numerator = ModLong(prime, initialValue = Factorial(n).reduceModPrime(prime))
		val denominator1 = Factorial(k).reduceModPrime(prime)
		val denominator2 = Factorial(n - k).reduceModPrime(prime)
		val res = numerator * denominator1.modInv(prime) * denominator2.modInv(prime)
		return res.value
	}
}