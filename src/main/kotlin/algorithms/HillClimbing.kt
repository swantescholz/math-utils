package algorithms

import extensions.abs
import math.DEFAULT_EPSILON
import math.linearalgebra.Vector2
import util.printlnRegularly

fun hillClimbing1d(functionToMaximize: (x: Double) -> Double, initialGuess: Double = 0.0,
                   initialStepSize: Double = 1.0, epsilon: Double = DEFAULT_EPSILON): Double {
	var currentPoint = initialGuess
	var stepSize = initialStepSize
	val acceleration = 1.2 // a value such as 1.2 is common
	val accelerations = arrayListOf(-acceleration, -1 / acceleration, 0.0, 1 / acceleration, acceleration)
	while (true) {
		val before = functionToMaximize(currentPoint)

		var best = -1
		var bestScore = Double.NEGATIVE_INFINITY
		for (j in 0..4) {
			// try each of 5 candidate locations
			currentPoint += stepSize * accelerations[j]
			val tmp = functionToMaximize(currentPoint)
			currentPoint -= stepSize * accelerations[j]
			if (tmp > bestScore) {
				bestScore = tmp
				best = j
			}
		}
		if (best != 2) {
			currentPoint += stepSize * accelerations[best]
			stepSize *= accelerations[best] // accelerate
		} else {
			stepSize /= acceleration
		}
		if (stepSize < epsilon && (functionToMaximize(currentPoint - before) < epsilon))
			break
	}
	return currentPoint
}

// TODO not working well
// returns the (x,y) that maximizes the given function
fun hillClimbing2d(functionToMaximize: (x: Double, y: Double) -> Double, initialGuess: Vector2 = Vector2.ZERO,
                   initialStepSizes: Vector2 = Vector2.XY, epsilon: Double = DEFAULT_EPSILON): Vector2 {
	val currentPoint = initialGuess.toArrayList()
	val stepSizes = initialStepSizes.toArrayList()
	val acceleration = 1.2 // a value such as 1.2 is common
	val accelerations = arrayListOf<Double>(-acceleration, -1 / acceleration, 0.0, 1 / acceleration, acceleration)
	while (true) {
		val before = functionToMaximize(currentPoint[0], currentPoint[1])
		for (i in 0..1) {
			var best = -1
			var bestScore = Double.NEGATIVE_INFINITY
			for (j in 0..4) {
				// try each of 5 candidate locations
				currentPoint[i] += stepSizes[i] * accelerations[j]
				val tmp = functionToMaximize(currentPoint[0], currentPoint[1])
				currentPoint[i] -= stepSizes[i] * accelerations[j]
				if (tmp > bestScore) {
					bestScore = tmp
					best = j
				}
			}
			if (best != 2 && Math.abs(bestScore - before) > epsilon) {
				currentPoint[i] += stepSizes[i] * accelerations[best]
				stepSizes[i] *= accelerations[best] // accelerate
			} else {
				stepSizes[i] /= acceleration
			}
		}
		printlnRegularly(stepSizes, before)
		if (stepSizes.all { it.abs() < epsilon } &&
				(functionToMaximize(currentPoint[0], currentPoint[1]) - before) < epsilon)
			break
	}
	return Vector2(currentPoint[0], currentPoint[1])
}