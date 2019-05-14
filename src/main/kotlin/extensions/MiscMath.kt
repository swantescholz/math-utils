package extensions

import org.apache.commons.math3.complex.Complex


val Complex.polarCoordinates: Pair<Double, Double>
	get() = Pair((real * real + imaginary * imaginary).sqrt(), Math.atan2(imaginary, real))

operator fun Complex.plus(b: Double) = add(b)
operator fun Complex.plus(b: Complex) = add(b)
operator fun Complex.minus(b: Double) = subtract(b)
operator fun Complex.minus(b: Complex) = subtract(b)
operator fun Complex.times(b: Double) = multiply(b)
operator fun Complex.times(b: Complex) = multiply(b)
operator fun Complex.div(b: Double) = divide(b)
operator fun Complex.div(b: Complex) = divide(b)
operator fun Complex.unaryMinus() = negate()

operator fun Double.plus(o: Complex) = o + this
operator fun Double.minus(o: Complex) = o - this
operator fun Double.times(o: Complex) = o * this
operator fun Double.div(o: Complex) = Complex(this) / o