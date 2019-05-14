package math.fractions

import extensions.*
import extensions.sequences.takeLast
import math.BIG0
import math.BIG1
import math.IMAX
import math.LMAX
import math.factorization.coprime
import org.apache.commons.math3.fraction.BigFraction
import util.astEqual
import util.astLess
import util.astLessEqual
import util.astTrue
import util.extensions.big
import java.math.BigInteger
import java.util.*


data class LongFraction(var n: Long, var d: Long) {
	fun toDouble(): Double {
		return n.toDouble() / d.toDouble()
	}
	
	fun bigf(): BigFraction = n.bigf(d)
}

class ContinuedFraction(val start: ArrayList<Int>, val period: ArrayList<Int> = ArrayList()) {
	
	private fun evaluatePeriod(): Double {
		if (period.isEmpty())
			return 0.0
		var res = 1.0
		val reversedPeriod = period.reversed()
		for (i in 1..60 / reversedPeriod.size) {
			for (element in reversedPeriod) {
				res = element + 1.0 / res
			}
		}
		return res
	}
	
	fun evaluate(): Double {
		var res = evaluatePeriod()
		for (it in start.reversed()) {
			res = it + 1.0 / res
		}
		return res
	}
	
	fun computeBestBoundedRationalApproximation(
			maxDenominator: Long,
			compareFractions: (LongFraction, LongFraction) -> Int = { a, b ->
				evaluate().let { x ->
					(a.n.toDouble() / a.d - x).abs().compareTo(
							(b.n.toDouble() / b.d - x).abs())
				}
			}): LongFraction {
		val lastTwoApproximations = genBestRationalApproximations().takeWhile {
			it.denominator.toLong() <= maxDenominator
		}.takeLast(2)
		astEqual(lastTwoApproximations.size, 2, "maxDenominator is too small; not enough exact best rational approximations found!")
		val (a, b) = lastTwoApproximations
		val (aa, ab) = Pair(a.n.toLong(), a.d.toLong())
		val (ba, bb) = Pair(b.n.toLong(), b.d.toLong())
		astTrue(b.n < LMAX.big && b.d < LMAX.big)
		var (da, db) = Pair(aa, ab)
		var bestN = ba
		var bestD = bb
		
		while (true) {
			da += ba
			db += bb
			if (db > maxDenominator)
				break
			if (compareFractions(LongFraction(bestN, bestD),
					LongFraction(da, db)) >= 0) {
				bestN = da
				bestD = db
			}
		}
		astTrue(bestN coprime bestD)
		return LongFraction(bestN, bestD)
	}
	
	fun genBestRationalApproximations(): Sequence<BigFraction> {
		var it = start.iterator()
		return genBestRationalApproximations(generateSequence {
			if (!it.hasNext())
				it = period.iterator()
			if (!it.hasNext())
				return@generateSequence null
			it.next()
		})
	}
	
	// takes e.g. [1,2,2,2,...] for sqrt(2), returns sequence of fractions
	private fun genBestRationalApproximations(xs: Sequence<Int>): Sequence<BigFraction> {
		val iter = xs.iterator()
		val first = Tuple4(BigInteger.valueOf(iter.next().toLong()),
				BIG1, BIG1, BIG0)
		return generateSequence(first) { last ->
			var res: Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>? = null
			if (iter.hasNext()) {
				val x = BigInteger.valueOf(iter.next().toLong())
				res = Tuple4(last.b + last.a * x,
						last.a, last.d + last.c * x, last.c)
			}
			res
		}.map { BigFraction(it.a, it.c) }
	}
	
	
	override fun toString(): String {
		return "($start, $period)"
	}
	
	companion object {
		fun fromStartIntAndPeriod(start: Int, period: ArrayList<Int> = ArrayList()): ContinuedFraction {
			return ContinuedFraction(arrayListOf(start), period)
		}
		
		fun fromSqrt(squaredSqrt: Long): ContinuedFraction {
			val period = ArrayList<Int>()
			val sqrt = squaredSqrt.sqrt()
			val start = squaredSqrt.floorSqrt()
			if (start * start == squaredSqrt)
				return ContinuedFraction.fromStartIntAndPeriod(start.toInt(), period)
			var a: Long
			var b = -start
			var c = 1L
			val n = squaredSqrt.toInt()
			while (true) {
				a = -b
				b = n - (b * b)
				assert(c divides b)
				b /= c
				val p = ((sqrt + a) / b).toInt()
				c = b
				b = a - p * b
				astLess(p, 1000000)
				period.add(p)
				if (c == 1L && b == -start)
					break
			}
			return ContinuedFraction.fromStartIntAndPeriod(start.toInt(), period)
		}
		
		fun fromDouble(doubleValue: Double): ContinuedFraction {
			astLessEqual(doubleValue.toInt(), IMAX)
			val start = ArrayList<Int>()
			var x = doubleValue
			for (_foo in 1..40) {
				val i = x.toInt()
				start.add(i)
				x -= i
				if (x == 0.0)
					break
				x = 1.0 / x
			}
			return ContinuedFraction(start)
		}
	}
}



