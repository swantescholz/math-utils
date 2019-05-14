package math.geometry

import extensions.floorSqrt
import extensions.sequences.seq
import math.factorization.gcd

// points on edges are not included
fun countLatticePointsInAATriangle(width: Long, height: Long): Long {
	return ((width + 1) * (height + 1) - 1 - width.gcd(height)) / 2 - width - height + 1
}

// gauss circle problem: count solutions for a*a+b*b <= radiusSquared
// for strictly inside (a*a+b*b < radiusSquared) just subtract one from squared radius
fun countLatticePointsInOrOnCircle(radiusSquared: Long): Long {
	val rl = (radiusSquared).floorSqrt()
	return 1L + 4 * (rl + 1L.seq(rl).map { (radiusSquared - it * it).floorSqrt() }.sum())
}