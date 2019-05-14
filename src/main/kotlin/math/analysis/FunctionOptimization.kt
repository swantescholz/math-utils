package math.analysis

import extensions.abs


fun findZerosOfIncreasingFunction2L(function: (Long, Long) -> Double, maxValue: Long = 100000): Triple<Long, Long, Double>? {
	if (function(0L, 0L) >= 0.0)
		throw IllegalArgumentException("function must be negative at 0,0")
	var minDiff = Double.MAX_VALUE
	var bestKey = Pair(0L, 0L)
	var x = 0L
	var y = 0L
	while (function(x, y) < 0.0 && x < maxValue) {
		x++
	}
	if (x == maxValue) {
		while (function(x, y) < 0.0 && y < maxValue) {
			y++
		}
		if (y == maxValue)
			return null
		y--
	}

	while (x > 0 && y < maxValue) {
		val fa = function(x - 1, y).abs()
		val fb = function(x, y + 1).abs()
		var fc = fa
		if (fb < fa) {
			y++
			fc = fb
		} else {
			x--
		}
		if (fc < minDiff) {
			minDiff = fc
			bestKey = Pair(x, y)
		}
	}
	return Triple(bestKey.first, bestKey.second, minDiff)
}
