package extensions

import org.apache.commons.math3.fraction.BigFraction
import java.math.BigInteger

operator fun Int.plus(you: BigFraction) = bigf().add(you)
operator fun Int.minus(you: BigFraction) = bigf().subtract(you)
operator fun Int.times(you: BigFraction) = bigf().multiply(you)
operator fun Int.div(you: BigFraction) = bigf().divide(you)
operator fun Long.plus(you: BigFraction) = bigf().add(you)
operator fun Long.minus(you: BigFraction) = bigf().subtract(you)
operator fun Long.times(you: BigFraction) = bigf().multiply(you)
operator fun Long.div(you: BigFraction) = bigf().divide(you)
operator fun BigInteger.unaryMinus() = BigInteger.ZERO.minus(this)
operator fun BigInteger.plus(you: BigFraction) = bigf().add(you)
operator fun BigInteger.minus(you: BigFraction) = bigf().subtract(you)
operator fun BigInteger.times(you: BigFraction) = bigf().multiply(you)
operator fun BigInteger.div(you: BigFraction) = bigf().divide(you)
operator fun BigFraction.unaryMinus() = BigFraction.ZERO.minus(this)
operator fun BigFraction.plus(you: BigFraction) = add(you)
operator fun BigFraction.minus(you: BigFraction) = subtract(you)
operator fun BigFraction.times(you: BigFraction) = multiply(you)
operator fun BigFraction.div(you: BigFraction) = divide(you)
operator fun BigFraction.plus(you: Int) = add(you)
operator fun BigFraction.minus(you: Int) = subtract(you)
operator fun BigFraction.times(you: Int) = multiply(you)
operator fun BigFraction.div(you: Int) = divide(you)
operator fun BigFraction.plus(you: Long) = add(you)
operator fun BigFraction.minus(you: Long) = subtract(you)
operator fun BigFraction.times(you: Long) = multiply(you)
operator fun BigFraction.div(you: Long) = divide(you)
operator fun BigFraction.plus(you: BigInteger) = add(you)
operator fun BigFraction.minus(you: BigInteger) = subtract(you)
operator fun BigFraction.times(you: BigInteger) = multiply(you)
operator fun BigFraction.div(you: BigInteger) = divide(you)

fun Int.bigf(den: Int = 1) = BigFraction(this, den)
fun Long.bigf(den: Long = 1) = BigFraction(this, den)

operator fun BigFraction.component1(): BigInteger = numerator
operator fun BigFraction.component2(): BigInteger = denominator
val BigFraction.n: BigInteger
	get() = this.numerator
val BigFraction.d: BigInteger
	get() = this.denominator