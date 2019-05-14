package math

import extensions.toArrayList
import math.combinatoric.BinomialCoefficient
import math.combinatoric.nCr
import math.primes.PRIMES
import math.primes.seqPrimes
import org.junit.Before
import org.junit.Test
import util.astEqual
import util.extensions.big

class BinomialCoefficientTest {

	@Before
	fun setUp() {
		PRIMES = seqPrimes(1000 * 1 * 1).map { it.toLong() }.toArrayList()
	}

	@Test
	fun testCompute() {
		astEqual(0L.big, BinomialCoefficient(3, -1).compute())
		astEqual(13L.big, BinomialCoefficient(13, 1).compute())
		astEqual(1287L.big, BinomialCoefficient(13, 8).compute())
	}

	@Test
	fun testModPrime() {
		astEqual(1287L % 7, BinomialCoefficient(13, 8).modPrime(7L))
		val bino = BinomialCoefficient(100, 10)
		val product = 100L.nCr(10)
		astEqual(product, bino.compute().toLong())
		for (p in PRIMES.take(20)) {
			astEqual(product % p, bino.modPrime(p))
		}
	}

}