package math.equations

import extensions.cbrt
import extensions.cube
import extensions.sqrt
import extensions.square
import org.apache.commons.math3.complex.Complex

fun findZerosForCubicPolynomialXXXPlusAXXPlusBXPlusC(a: Double, b: Double, c: Double): Triple<Complex, Complex, Complex> {
	val p = -a / 3
	val q = p * p * p + (a * b - 3 * c) / 6
	val r = b / 3
	val n = p + (q + (q * q + (r - p * p).cube()).sqrt()).cbrt() +
			(q - (q * q + (r - p * p).cube()).sqrt()).cbrt()
	val discriminant = (a + n).square() / 4 - b - n * (a + n)
	val left = -(a + n) / 2
	if (discriminant < 0) {
		val root = (-discriminant).sqrt()
		return Triple(Complex(n), Complex(left, root), Complex(left, -root))
	}
	val root = discriminant.sqrt()
	return Triple(Complex(n), Complex(left + root), Complex(left - root))
}