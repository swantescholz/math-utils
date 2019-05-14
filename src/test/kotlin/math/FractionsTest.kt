package math

import math.fractions.FiniteContinuedFraction
import org.apache.commons.math3.fraction.Fraction
import org.junit.Assert
import org.junit.Test

class FractionTest {

	@Test
	fun testFromFaction() {
		val e = FiniteContinuedFraction(listOf(3, 4, 12, 4))
		val a = FiniteContinuedFraction.fromFraction(Fraction(649, 200))
		Assert.assertEquals(e, a)
	}

	@Test
	fun testToFaction() {
		val e = Fraction(649, 200)
		val a = FiniteContinuedFraction(listOf(3, 4, 12, 4)).toFraction()
		Assert.assertEquals(e, a)
	}

}