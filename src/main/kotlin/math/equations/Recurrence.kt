package math.equations

import extensions.Tuple4
import extensions.sqrt

// for a_n = Aa_{n-1} + Ba_{n-2}, returns (C,x,D,y) s.t. a_n = C*x^n+D*y^n
fun solveRecurrenceRelation(a0: Double, a1: Double, A: Double, B: Double)
		: Tuple4<Double, Double, Double, Double> {
	val sqrt = (A * A / 4 + B).sqrt()
	val x = A / 2 + sqrt
	val y = A / 2 - sqrt
	val C = (a1 - a0 * y) / (x - y)
	val D = a0 - C
	return Tuple4(C, x, D, y)
}