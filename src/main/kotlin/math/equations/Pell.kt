package math.equations

import extensions.component1
import extensions.component2
import extensions.d
import extensions.n
import math.BIG1
import math.BIGN1
import math.fractions.ContinuedFraction
import math.sequences.infSequence
import java.math.BigInteger

// x*x-d*y*y = +-1
fun genPellEquationSolutions(d: Long, rightSidePositive: Boolean = true): Sequence<Pair<BigInteger, BigInteger>> {
	val cf = ContinuedFraction.fromSqrt(d)
	val (start, period) = Pair(cf.start, cf.period)
	val bigd = BigInteger.valueOf(d)
	val rightSide = if (rightSidePositive) BIG1 else BIGN1
	val (x1, y1) = cf.genBestRationalApproximations().filter {
		it.n * it.n - bigd * it.d * it.d == rightSide
	}.first()
	var xk = x1
	var yk = y1
	val res = infSequence().map {
		val xold = xk
		val yold = yk
		xk = x1 * xold + bigd * y1 * yold
		yk = x1 * yold + y1 * xold
		Pair(xold, yold)
	}
	if (rightSidePositive)
		return res
	return res.withIndex().filter { it.index % 2L == 0L }.map { it.value }
}