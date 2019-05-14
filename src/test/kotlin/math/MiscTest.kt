package math

import math.modular.solveLinearCongruence
import org.junit.Assert
import org.junit.Test


class MyTest {

	@Test
	fun testCongruence() {
		Assert.assertNull(solveLinearCongruence(8, 3, 10))
		Assert.assertEquals(4, solveLinearCongruence(5, 6, 7))
		Assert.assertEquals(8, solveLinearCongruence(15, 5, 35))
	}

}