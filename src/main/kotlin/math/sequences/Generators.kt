package math.sequences

import extensions.Tuple4
import extensions.sequences.intertwine
import extensions.sequences.seq
import extensions.sequences.takeUntilNull
import math.fractions.LongFraction
import java.math.BigInteger

fun genCenteredBidirectionalCoords1D(): Sequence<Long> {
	return (0L downTo Long.MIN_VALUE).asSequence().intertwine(1L.seq())
}

fun farey(n: Int, ascending: Boolean = true): Sequence<LongFraction> {
	var (a, b, c, d) = Tuple4(0L, 1L, 1L, n.toLong())
	if (!ascending) {
		a = 1
		c = n - 1L
	}
	return infSequence().map {
		if ((ascending && c > n) || (!ascending && a <= 0))
			return@map null
		val k = ((n + b) / d)
		val (olda, oldb) = Pair(a, b)
		a = c
		b = d
		c = k * c - olda
		d = k * d - oldb
		return@map LongFraction(a, b)
	}.takeUntilNull()


}

fun genTriangleNumbers() = infSequence(0).map { it * (it + 1) / 2 }

fun genSquareNumbers() = 0L.seq().map { it * it }

fun genPentagonalNumbers() = infSequence(0).map { i -> i * (3 * i - 1) / 2 }

fun genHexagonalNumbers() = infSequence(0).map { i -> i * (2 * i - 1) }

fun genHeptagonalNumbers() = infSequence(0).map { i -> i * (5 * i - 3) / 2 }

fun genOctagonalNumbers() = infSequence(0).map { i -> i * (3 * i - 2) }


fun genGrowing2DCoordinates(): Sequence<Pair<Int, Int>> {
	return 0.seq().flatMap { n ->
		0.seq(n).map { Pair(it, n - it) }
	}
}

fun infRange(startValue: Long = 0, stepSize: Long = 1): LongProgression {
	return LongProgression.fromClosedRange(startValue, Long.MAX_VALUE, stepSize)
}

fun infSequence(startValue: Long = 0, stepSize: Long = 1): Sequence<Long> {
	return LongProgression.fromClosedRange(startValue, Long.MAX_VALUE, stepSize).asSequence()
}

fun infSequenceBig(startValue: Long = 0, stepSize: Long = 1): Sequence<BigInteger> {
	val ssbi = BigInteger.valueOf(stepSize)
	return generateSequence(BigInteger.valueOf(startValue)) { it + ssbi }
}