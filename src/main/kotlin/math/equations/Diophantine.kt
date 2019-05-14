package math.equations

import extensions.divides
import math.modular.euclidExtended

fun hasLinearDiophantineEquationNonNegativeSolution(a: Long, b: Long, c: Long): Boolean {
	if (a divides c)
		return true
	if (b divides c)
		return true
	return hasLinearDiophantineEquationPositiveSolution(a, b, c)
}

// ax+by=c, x,y > 0
fun hasLinearDiophantineEquationPositiveSolution(a: Long, b: Long, c: Long): Boolean {
	var (x0, y0, d) = euclidExtended(a, b)
	if (!(d divides c))
		return false
	x0 *= (c / d)
	y0 *= (c / d)
	if (x0 > 0 && y0 > 0)
		return true
	if (x0 <= 0 && y0 <= 0)
		return false
	val kbase = if (x0 <= 0) {
		(d * x0) / b
	} else {
		(-d * y0) / a
	}
	for (k in kbase - 1..kbase + 1) {
		val (x1, y1) = Pair(x0 - k * b / d, y0 + k * a / d)
		if (x1 > 0 && y1 > 0)
			return true
	}
	return false
}