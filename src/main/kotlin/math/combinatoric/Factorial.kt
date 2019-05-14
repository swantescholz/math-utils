package math.combinatoric

import datastructures.ModLong
import extensions.factorial
import extensions.modPow
import extensions.toInt
import util.extensions.big
import java.math.BigInteger

data class Factorial(val n: Long) {

	fun compute(): BigInteger {
		return n.big.factorial()
	}

	// computes x % prime with x and prime being coprime, and x * prime^k = this
	fun reduceModPrime(prime: Long): Long {
		if (n <= 1L)
			return 1L
		var res = ModLong(prime, initialValue = 1L)
		val countCompletePrimeCircles = n / prime
		val highRemainder = n % prime
		for (remainder in 1L..prime - 1) {
			val occurrenceCount = countCompletePrimeCircles +
					(remainder <= highRemainder).toInt()
			res *= remainder.modPow(occurrenceCount, prime)
		}
		return (res * Factorial(countCompletePrimeCircles).reduceModPrime(prime)).value
	}
}