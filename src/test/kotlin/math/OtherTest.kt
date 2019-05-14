package math

import math.combinatoric.Factorial
import org.junit.Test
import util.astEqual
import util.extensions.big

class OtherTest {

	@Test
	fun testFactorial() {
		for (prime in listOf(2L, 3, 5, 7, 11)) {
			var product = 1L.big
			for (n in 1..100L) {
				product *= n.big
				val fact = Factorial(n)
				astEqual(fact.compute(), product)
				val reduced = fact.reduceModPrime(prime)
				var x = product
				while (x % prime.big == 0L.big) {
					x /= prime.big
				}
				astEqual(x % prime.big, reduced.big)
			}
		}
	}

}