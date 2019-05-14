package math.analysis

import extensions.div
import extensions.times
import org.apache.commons.math3.fraction.BigFraction

data class SqrtBf(val squaredNumber: BigFraction) : Comparable<SqrtBf> {
	override fun compareTo(other: SqrtBf): Int =
			squaredNumber.compareTo(other.squaredNumber)
	
	override fun toString() = "sqrt($squaredNumber) ~ $doubleValue"
	
	val doubleValue: Double
		get() = Math.sqrt(squaredNumber.toDouble())
	
	operator fun times(o: SqrtBf) = SqrtBf(squaredNumber * o.squaredNumber)
	operator fun times(o: BigFraction) = SqrtBf(squaredNumber * (o * o))
	operator fun div(o: SqrtBf) = SqrtBf(squaredNumber / o.squaredNumber)
	
	operator fun div(o: BigFraction) = div(SqrtBf(o * o))
}