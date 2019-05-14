package extensions

import org.apache.commons.math3.fraction.Fraction

operator fun Int.plus(you: Fraction) = toFraction().add(you)
operator fun Int.minus(you: Fraction) = toFraction().subtract(you)
operator fun Int.times(you: Fraction) = toFraction().multiply(you)
operator fun Int.div(you: Fraction) = toFraction().divide(you)
operator fun Long.plus(you: Fraction) = toFraction().add(you.toInt())
operator fun Long.minus(you: Fraction) = toFraction().subtract(you)
operator fun Long.times(you: Fraction) = toFraction().multiply(you.toInt())
operator fun Long.div(you: Fraction) = toFraction().divide(you.toInt())
operator fun Fraction.plus(you: Fraction) = add(you)
operator fun Fraction.minus(you: Fraction) = subtract(you)
operator fun Fraction.times(you: Fraction) = multiply(you)
operator fun Fraction.div(you: Fraction) = divide(you)
operator fun Fraction.plus(you: Int) = add(you)
operator fun Fraction.minus(you: Int) = subtract(you)
operator fun Fraction.times(you: Int) = multiply(you)
operator fun Fraction.div(you: Int) = divide(you)
operator fun Fraction.plus(you: Long) = add(you.toInt())
operator fun Fraction.minus(you: Long) = subtract(you.toInt())
operator fun Fraction.times(you: Long) = multiply(you.toInt())
operator fun Fraction.div(you: Long) = divide(you.toInt())

operator fun Fraction.component1() = numerator
operator fun Fraction.component2() = denominator

fun Long.toFraction(den: Int = 1) = Fraction(this.toInt(), den.toInt())
fun Int.toFraction(den: Int = 1) = Fraction(this, den)