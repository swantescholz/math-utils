package math.sequences

import extensions.div
import extensions.plus
import extensions.times
import util.extensions.big
import java.math.BigInteger

fun sumOfNumbersUpTo(max: Int): Long {
	if (max <= 0)
		return 0
	return max * (max + 1L) / 2
}

fun sumOfNumbersUpTo(max: Long): BigInteger {
	if (max <= 0)
		return 0.big
	return max.big * (max + 1) / 2
}

fun sumOfOddNumbersUpTo(max: Long): BigInteger {
	if (max <= 0)
		return 0.big
	return sumOfNumbersUpTo(max) - sumOfNumbersUpTo(max / 2) * 2
}

fun sumOfEvenNumbersUpTo(max: Long): BigInteger {
	if (max <= 0)
		return 0.big
	return sumOfNumbersUpTo(max / 2) * 2
}

fun sumOfSquaresUpTo(maxBase: Long): BigInteger {
	if (maxBase <= 0)
		return 0.big
	return maxBase.big * (maxBase + 1) * (2 * maxBase + 1) / 6
}

fun sumOfEvenSquaresUpTo(maxBase: Long): BigInteger {
	if (maxBase <= 0)
		return 0.big
	val evenMax = maxBase / 2 * 2
	return (sumOfSquaresUpTo(evenMax) + (evenMax + 1) / 2) / 2 + sumOfOddNumbersUpTo(evenMax)
}

fun sumOfOddSquaresUpTo(maxBase: Long): BigInteger {
	if (maxBase <= 0)
		return 0.big
	return sumOfSquaresUpTo(maxBase) - sumOfEvenSquaresUpTo(maxBase)
}

fun sumOfAlternateSquares(maxBase: Long): BigInteger {
	if (maxBase <= 0)
		return 0.big
	return (sumOfEvenSquaresUpTo(maxBase) - sumOfOddSquaresUpTo(maxBase)).abs()
}