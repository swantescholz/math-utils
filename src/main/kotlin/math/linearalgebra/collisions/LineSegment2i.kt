package math.linearalgebra.collisions

import extensions.*
import org.apache.commons.math3.fraction.Fraction

data class LineSegmenti(val a: Coord2i, val b: Coord2i) {
	fun intersect(you: LineSegmenti): Pair<Fraction, Fraction>? {
		val d1 = b - a
		val d2 = you.b - you.a
		val denom = d2.y * d1.x - d2.x * d1.y
		if (denom == 0)
			return null // parallel
		val t = (d1.x * (a.y - you.a.y) + (you.a.x - a.x) * d1.y).toFraction() / denom
		if (t <= Fraction.ZERO || t >= Fraction.ONE)
			return null
		val s = (t * d2.x + you.a.x - a.x) / d1.x
		if (s <= Fraction.ZERO || s >= Fraction.ONE)
			return null
		return Pair(s * d1.x + a.x, s * d1.y + a.y)
	}
}

