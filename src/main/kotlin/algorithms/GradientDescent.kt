package algorithms

import math.DEFAULT_EPSILON
import math.linearalgebra.VectorD

// return vector x such that x is (close to) a minimum
fun gradientDescent(initialGuess: VectorD, initialStepSizeGamma: Double = 0.001, derive: (VectorD) -> VectorD,
                    tolerance: Double = DEFAULT_EPSILON): VectorD {
	var x = initialGuess
	var gamma = initialStepSizeGamma
	val epsilon = tolerance*tolerance
	var lastX = x
	var lastDerivative = derive(x)
	x -= lastDerivative * gamma
	var xDifference = x - lastX
	while (xDifference.length2() > epsilon) {
		val derivative = derive(x)
		val derivativeDifference = derivative - lastDerivative
		gamma = (xDifference * derivativeDifference) / derivativeDifference.length2()
		lastX = x
		x -= derivative * gamma
		xDifference = x - lastX
		lastDerivative = derivative
	}
	return x
}