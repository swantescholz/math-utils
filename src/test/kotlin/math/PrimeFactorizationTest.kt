package math

import extensions.toArrayList
import math.factorization.PrimeFactorization
import math.primes.PRIMES
import math.primes.seqPrimes
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PrimeFactorizationTest {

	@Before
	fun setUp() {
		PRIMES = seqPrimes(1000 * 1000 * 1).map { it.toLong() }.toArrayList()
		//	PRIME_SET = HashSet(PRIMES)
	}

	@Test
	fun testProduct() {
		val a = PrimeFactorization.factorize(350)
		val b = PrimeFactorization.factorize(1300)
		val c = a * b
		Assert.assertEquals(c.primeFactors, arrayListOf(2L, 5, 7, 13))
		Assert.assertEquals(c.exponents, arrayListOf(3, 4, 1, 1))
		Assert.assertEquals(c.product, 350 * 1300)

	}

}