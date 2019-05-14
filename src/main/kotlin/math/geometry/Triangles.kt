package math.geometry

import extensions.Tuple
import extensions.sqrt
import math.linearalgebra.Vector2

// assumes a is base length, b is right line, c is left line
// A is (0,0), B is (a,0), C is the intersection of the b and c lines
// alpha is at A, beta at B, gamma at C
data class Triangle(val a: Double, val b: Double, val c: Double) {
	val A: Vector2 get() = Vector2(0, 0)
	val B: Vector2 get() = Vector2(a, 0.0)
	val C: Vector2 get() {
		val x = 0.5 * (a * a + c * c - b * b) / a
		val y = (c * c - x * x).sqrt()
		return Vector2(x, y)
	}
	val alpha: Double get() = Math.asin(heightOnSideA / c)
	val beta: Double get() = Math.asin(heightOnSideB / a)
	val gamma: Double get() = Math.asin(heightOnSideC / b)
	val heightOnSideA: Double = C.y
	val heightOnSideB: Double get() {
		val x = 0.5 * (b * b + a * a - c * c) / b
		return (a * a - x * x).sqrt()
	}
	val heightOnSideC: Double get() {
		val x = 0.5 * (c * c + b * b - a * a) / c
		return (b * b - x * x).sqrt()
	}
	val area: Double get() {
		val s = 0.5 * (a + b + c)
		return Math.sqrt(s * (s - a) * (s - b) * (s - c))
	}
	val circumference: Double get() = a + b + c
	val circumcircleRadius: Double get() {
		return a * b * c / Math.sqrt((a + b + c) * (a + b - c) * (a - b + c) * (-a + b + c))
	}
	val circumcenter: Vector2 get() {
		val r = circumcircleRadius
		return Triangle(a, r, r).C
	}
	val bisectorGammaHitsSideA: Vector2 get() {
		val leftTriangle = Triangle.fromAngleAngleSide(gamma * 0.5, alpha, c)
		return Vector2(leftTriangle.b, 0.0)
	}
	val bisectorAlphaHitsSideB: Vector2 get() {
		return Triangle.fromAngleAngleSide(alpha * 0.5, beta, a).C
	}
	val bisectorBetaHitsSideC: Vector2 get() {
		return Triangle.fromAngleAngleSide(alpha, beta * 0.5, a).C
	}
	
	
	companion object {
		fun isPointInTriangle(a: Vector2, b: Vector2, c: Vector2, p: Vector2): Boolean {
			val (d, e, f, pa, pb, pc) = Tuple(b - a, c - b, a - c, p - a, p - b, p - c)
			return (pa.isLeftOf(d) && pb.isLeftOf(e) && pc.isLeftOf(f)) ||
					(pa.isRightOf(d) && pb.isRightOf(e) && pc.isRightOf(f))
		}
		
		fun fromPoints(A: Vector2, B: Vector2, C: Vector2): Triangle {
			val a = B.distanceTo(A)
			val b = C.distanceTo(B)
			val c = A.distanceTo(C)
			return Triangle(a, b, c)
		}
		
		fun fromAngleAngleSide(alpha: Double, beta: Double, a: Double = 1.0): Triangle {
			val tana = Math.tan(alpha)
			val tanb = Math.tan(beta)
			val w = a * tanb / (tana + tanb)
			val h = w * tana
			val C = Vector2(w, h)
			val b = C.distanceTo(Vector2(a, 0.0))
			return Triangle(a, b, C.length())
		}
	}
}