package math.analysis

import extensions.component1
import extensions.component2
import extensions.floorSqrt
import extensions.square
import math.BIG4
import org.apache.commons.math3.fraction.BigFraction
import util.astTrue
import java.math.BigInteger

data class SqrtB(val squaredNumber: BigInteger) : Comparable<SqrtB> {
	override fun compareTo(other: SqrtB): Int =
			squaredNumber.compareTo(other.squaredNumber)
	
	operator fun compareTo(o: BigFraction) =
			(squaredNumber * o.denominator * o.denominator).compareTo(o.numerator * o.numerator)
	
	override fun toString() = "sqrt($squaredNumber) ~ $floor ~ $doubleValue"
	
	val doubleValue: Double
		get() = Math.sqrt(squaredNumber.toDouble())
	val floor: BigInteger
		get() = squaredNumber.floorSqrt()
	
	operator fun times(o: SqrtB) = SqrtB(squaredNumber * o.squaredNumber)
	operator fun times(o: BigInteger) = SqrtB(squaredNumber * (o * o))
	operator fun div(o: SqrtB) {
		val result = squaredNumber / o.squaredNumber
		astTrue(result * o.squaredNumber == squaredNumber)
		SqrtB(result)
	}
	
	operator fun div(o: BigInteger) = div(SqrtB(o * o))
	
	// returns -1/0/1 if |this-fraction1| </=/> |this-fraction2|
	fun compareDistanceToTwoFractions(fraction1: BigFraction, fraction2: BigFraction): Int {
		val (a, b) = fraction1
		val (c, d) = fraction2
		val lhs = ((a * d).square() - (b * c).square()).square()
		val rhs = ((b * d) * (a * d - b * c)).square() * squaredNumber * BIG4
		var result = lhs.compareTo(rhs)
		if (a * d < b * c)
			result *= -1 // switch sign, because of negative multiplication with (ad-bc)
		return result
	}
}