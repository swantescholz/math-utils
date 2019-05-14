package extensions

import extensions.sequences.seq
import extensions.sequences.takeUntilNull
import math.sequences.sumOfNumbersUpTo
import math.sequences.sumOfSquaresUpTo
import java.math.BigInteger

infix fun IntRange.step(stepSize: Double): Sequence<Double> {
	return this.start.toDouble()..this.endInclusive.toDouble() step stepSize
}

infix fun LongRange.step(stepSize: Double): Sequence<Double> {
	return this.start.toDouble()..this.endInclusive.toDouble() step stepSize
}

infix fun ClosedRange<Double>.step(stepSize: Double): Sequence<Double> {
	var value = this.start - stepSize
	return 1.seq().map {
		value += stepSize
		if (value > this.endInclusive)
			return@map null
		value
	}.takeUntilNull()
}

fun LongRange.sumOfSquares(): BigInteger {
	return sumOfSquaresUpTo(this.endInclusive) - sumOfSquaresUpTo(this.start - 1)
}

fun LongRange.fastSum(): BigInteger {
	return sumOfNumbersUpTo(this.endInclusive) - sumOfNumbersUpTo(this.start - 1)
}

fun IntRange.fastSum(): Long {
	return sumOfNumbersUpTo(this.endInclusive) - sumOfNumbersUpTo(this.start - 1)
}