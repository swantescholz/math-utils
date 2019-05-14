package math

import extensions.bigf
import math.analysis.SqrtB
import math.fractions.ContinuedFraction
import math.fractions.LongFraction
import org.apache.commons.math3.fraction.BigFraction
import org.junit.Test
import util.astEqual
import util.astTrue
import util.extensions.big

class ContinuedFractionTest {
	@Test
	fun testApproximation() {
		astTrue(SqrtB(3.big) < SqrtB(4.big))
		astTrue(SqrtB(3.big) < BigFraction(9, 5))
		astTrue(SqrtB(3.big) > BigFraction(6, 5))
		astEqual(-1, SqrtB(19.big).
				compareDistanceToTwoFractions(135.bigf(31), 74.bigf(14)))
		astEqual(1, SqrtB(37.big).
				compareDistanceToTwoFractions(31.bigf(7), 67.bigf(11)))
		fun func(n: Long): (LongFraction, LongFraction) -> Int {
			return { a: LongFraction, b: LongFraction ->
				SqrtB(n.big).compareDistanceToTwoFractions(a.bigf(), b.bigf())
			}
		}
		astEqual(ContinuedFraction.fromSqrt(13).computeBestBoundedRationalApproximation(20,
				func(13)),
				LongFraction(18, 5))
		astEqual(ContinuedFraction.fromSqrt(13).computeBestBoundedRationalApproximation(30,
				func(13)),
				LongFraction(101, 28))
	}
}