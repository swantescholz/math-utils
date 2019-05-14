package math.geometry

import extensions.round
import extensions.sorted
import extensions.sqrt
import extensions.sum
import math.equations.countAllSumOfSquaresRepresentations
import math.factorization.PrimeFactorization
import math.factorization.coprime
import util.astTrue

// one circle can be defined as encompassing the other two and the result, by making it's radius negative
fun computeRadiusOfCircleInGapOfThreeCircles(r1: Double, r2: Double, r3: Double): Double {
	astTrue(r1 != 0.0 && r2 != 0.0 && r3 != 0.0)
	val (a, b, c) = Triple(1.0 / r1, 1.0 / r2, 1.0 / r3)
	astTrue((a > 0 && b > 0) || (a > 0 && c > 0) || (b > 0 && c > 0), "at most one negative value")
	val lhs = a + b + c
	val rhs = 2 * (a * b + b * c + c * a).sqrt()
	val (res1, res2) = Pair(1.0 / (lhs - rhs), 1.0 / (lhs + rhs)).sorted()
	if (res1 < 0)
		return res2
	return res1
}

fun seqPrimitivePythagoreanTriples(circumferenceLimit: Long, filter: (Triple<Long, Long, Long>) -> Boolean = { true })
		: Sequence<Triple<Long, Long, Long>> {
	return (2L..(1 + (circumferenceLimit / 2).sqrt().round())).asSequence().map { m ->
		val nlimit = Math.min(m - 1, circumferenceLimit / (2 * m) - m)
		((m % 2 + 1)..nlimit step 2).asSequence().map { n ->
			val a = m * m - n * n
			val b = 2 * m * n
			Triple(Math.min(a, b), Math.max(a, b), m * m + n * n)
		}.filter { it.first coprime it.second && it.first coprime it.third && it.second coprime it.third }
				.filter { filter(it) }
	}.flatten().filter { it.sum() < circumferenceLimit }
}

fun countLatticePointsOnCircle(squaredRadius: Long): Long {
	val pf = PrimeFactorization.factorize(squaredRadius)
	return pf.countAllSumOfSquaresRepresentations()
}
