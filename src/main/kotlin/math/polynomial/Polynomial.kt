package math.polynomial

import math.CUBES
import math.CUBES_SET
import math.SQUARES
import math.SQUARES_SET


fun Long.isSquare(): Boolean {
	if (this <= SQUARES.last())
		return this in SQUARES_SET
	val x = Math.round(Math.sqrt(this.toDouble())).toLong()
	return x * x == this
}

fun Long.isCube(): Boolean {
	if (this <= CUBES.last())
		return this in CUBES_SET
	val x = Math.round(Math.pow(this.toDouble(), 1 / 3.0)).toLong()
	return x * x * x == this
}

fun Long.isTriangle(): Boolean {
	val nsq = 2 * this
	val n = Math.sqrt(nsq.toDouble()).toLong()
	return n * (n + 1) == nsq
}

fun Long.toPentagonal(): Long {
	return this * (3 * this - 1) / 2
}

fun Long.isPentagonal(): Boolean {
	val base = Math.sqrt(2.0 / 3 * this).toLong() + 1
	return base * (3 * base - 1) / 2L == this
}

fun Long.isHexagonal(): Boolean {
	val n = Math.sqrt(this / 2.0).toLong() + 1
	return n * (2 * n - 1) == this
}

fun Long.isHeptagonal(): Boolean {
	val n = Math.sqrt(this * 0.4).toLong() + 1
	return n * (5 * n - 3) / 2 == this
}

fun Long.isOctagonal(): Boolean {
	val n = Math.sqrt(this / 3.0).toLong() + 1
	return n * (3 * n - 2) == this
}

