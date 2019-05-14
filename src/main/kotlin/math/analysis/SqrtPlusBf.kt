package math.analysis

import extensions.minus
import extensions.plus
import extensions.sqrt
import extensions.times
import org.apache.commons.math3.fraction.BigFraction

// represents numbers of the form: summand + sqrt(discriminant)
data class SqrtPlusBf(val summand: BigFraction, val discriminant: BigFraction = BigFraction.ZERO) {
	
	fun square() = SqrtPlusBf(summand * summand + discriminant, summand * summand * 4 * discriminant)
	fun pow4() = square().square()
	
	infix operator fun plus(o: BigFraction) = SqrtPlusBf(summand + o, discriminant)
	infix operator fun minus(o: BigFraction) = SqrtPlusBf(summand - o, discriminant)
	infix operator fun times(o: BigFraction) = SqrtPlusBf(summand * o, discriminant * o * o)
	infix operator fun div(o: BigFraction) = this * o.reciprocal()
	
	override fun toString(): String = "$summand + sqrt($discriminant) ~ ${doubleValue()}"
	fun doubleValue() = discriminant.toDouble().sqrt() + summand.toDouble()
	
	companion object {
		fun sqrt(numberToTakeSqrtOf: BigFraction) = SqrtPlusBf(BigFraction.ZERO, numberToTakeSqrtOf)
	}
}