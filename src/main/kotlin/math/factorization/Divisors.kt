package math.factorization

import datastructures.ModLong
import extensions.*
import extensions.sequences.seq
import math.IMAX
import math.primes.PRIMES
import util.astGreater
import util.astGreaterEqual
import util.extensions.big
import util.printlnRegularly
import java.util.*

fun createDivisorCountArray(maxIncluding: Int): ShortArray {
	astGreater(PRIMES.last().big.nextProbablePrime().toLong().square(), maxIncluding.toLong())
	val a = ShortArray(maxIncluding + 1, { 1 })
	a[0] = 0
	a[1] = 1
	for (p in PRIMES) {
		if (p > maxIncluding)
			break
		var x = 1L
		for (exp in 1..IMAX) {
			x *= p
			if (x > maxIncluding)
				break
			for (factor in 1..IMAX) {
				val i = factor * x
				if (i > maxIncluding)
					break
				if (factor % p == 0L)
					continue
				i.toInt().let {
					a[it] = ((exp + 1) * a[it]).toShort()
				}
			}
		}
	}
	return a
}


// computes the desiredSum of (desiredSum of d for all divisors d of n) for all n <= maxIncluding
fun sumOfDivisor1Function(maxIncluding: Long, modulus: Long = Long.MAX_VALUE): Long {
	astGreaterEqual(maxIncluding, 1L)
	val mbig = modulus.big
	var res = ModLong(modulus)
	val sqrt = maxIncluding.floorSqrt()
	for (divisor in 1..sqrt) {
		res += ModLong(modulus, divisor) * (maxIncluding / divisor)
	}
	var lastLowestDivisor = maxIncluding + 1
	for (factor in 1.seq()) {
		val lowestDivisorForFactor = 1 + (maxIncluding / (factor + 1))
		if (lowestDivisorForFactor <= sqrt) {
			res += ((sqrt + 1..lastLowestDivisor - 1).fastSum() * factor).mod(mbig).toLong()
			break
		}
		res += ((lowestDivisorForFactor..lastLowestDivisor - 1).fastSum() * factor).mod(mbig).toLong()
		lastLowestDivisor = lowestDivisorForFactor
	}
	return res.value
}

// computes the desiredSum of (desiredSum of d^2 for all divisors d of n) for all n <= maxIncluding
fun sumOfDivisor2Function(maxIncluding: Long, modulus: Long = Long.MAX_VALUE): Long {
	astGreaterEqual(maxIncluding, 1L)
	val mbig = modulus.big
	var res = ModLong(modulus)
	val sqrt = maxIncluding.floorSqrt()
	for (divisor in 1..sqrt) {
		res += ModLong(modulus, divisor) * divisor * (maxIncluding / divisor)
		printlnRegularly(divisor)
	}
	var lastLowestDivisor = maxIncluding + 1
	for (factor in 1.seq()) {
		val lowestDivisorForFactor = 1 + (maxIncluding / (factor + 1))
		if (lowestDivisorForFactor <= sqrt) {
			res += ((sqrt + 1..lastLowestDivisor - 1).sumOfSquares() * factor.big).mod(mbig).toLong()
			break
		}
		res += ((lowestDivisorForFactor..lastLowestDivisor - 1).sumOfSquares() * factor.big).
				mod(mbig).toLong()
		lastLowestDivisor = lowestDivisorForFactor
		printlnRegularly(factor)
	}
	return res.value
}

tailrec fun Long.gcd(you: Long): Long {
	if (you == 0L)
		return this
	return you.gcd(this % you)
}

infix fun Long.coprime(you: Long): Boolean {
	return this.gcd(you) == 1L
}


// assumes primes are sufficient
fun Long.countDivisors(): Long {
	if (this <= 0)
		return 0L
	if (this == 1L)
		return 1L
	var product = 1L
	var nvar = this
	for (p in PRIMES) {
		var count = 1L
		while (nvar % p == 0L) {
			nvar /= p
			count++
			if (nvar == 1L)
				return product * count
		}
		product *= count
	}
	return -1L
}

fun Long.countDivisorsOfSquare(): Long {
	var product = 1L
	var nvar = this
	for (p in PRIMES) {
		var count = 0L
		while (nvar % p == 0L) {
			nvar /= p
			count++
			if (nvar == 1L)
				return product * ((count shl 1) + 1)
		}
		if (count > 0L)
			product *= (count shl 1) + 1
	}
	return -1L
}

fun Long.countPrimeDivisors(): Int {
	var nvar = this
	var count = 0
	for (p in PRIMES) {
		if (nvar % p == 0L) {
			count++
			do {
				nvar /= p
			} while (nvar % p == 0L)
			if (nvar == 1L)
				break
		}
	}
	//	astEqual(nvar, 1L)
	return count
}

// computes all devisors of the represented number in ascending order, including 1 and the number itself
fun PrimeFactorization.computeDivisors(): ArrayList<Long> {
	if (size == 0)
		return arrayListOf(1L)
	val res = ArrayList<Long>()
	fun frec(currentProduct: Long, primeFactorIndex: Int) {
		if (primeFactorIndex >= size) {
			res.add(currentProduct)
			return
		}
		var updatedProduct = currentProduct
		for (_i in 0..exponents[primeFactorIndex]) {
			frec(updatedProduct, primeFactorIndex + 1)
			updatedProduct *= primeFactors[primeFactorIndex]
		}
	}
	frec(1L, 0)
	res.sort()
	return res
}

fun Long.computeDivisors(): ArrayList<Long> {
	astGreaterEqual(this, 1L)
	return PrimeFactorization.factorize(this).computeDivisors()
}

