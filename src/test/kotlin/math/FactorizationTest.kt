package math

import extensions.toArrayList
import math.factorization.computeDivisors
import math.factorization.sumOfDivisor1Function
import math.primes.PRIMES
import math.primes.seqPrimes
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FactorizationTest {

	@Before
	fun setUp() {
		PRIMES = seqPrimes(1000 * 1000 * 1).map { it.toLong() }.toArrayList()
		//	PRIME_SET = HashSet(PRIMES)
	}

	@Test
	fun testComputeDivisors() {
		Assert.assertEquals(arrayListOf(1L, 2L, 3L, 4L, 6L, 12L), 12L.computeDivisors())
	}

	@Test
	fun testSumOfDivisor1Function() {
		var sum = 0L
		for (i in 1..10000L) {
			val x = sumOfDivisor1Function(i)
			sum += i.computeDivisors().sum()
			Assert.assertEquals(sum, x)
		}
	}

}