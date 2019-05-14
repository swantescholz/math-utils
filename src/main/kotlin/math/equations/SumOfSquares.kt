package math.equations

import extensions.*
import math.IMAX
import math.factorization.PrimeFactorization
import math.primes.isPrime
import util.astEqual
import util.astGreater
import util.astTrue
import util.astUnequal
import util.extensions.big
import java.math.BigInteger
import java.util.*

// transforms product of sums of squares to sums of squares (two solutions)
// (a*a+b*b)*(c*c+d*d) = (a*c-b*d)^2+(a*d+b*c)^2 = (a*c+b*d)^2+(a*d-b*c)^2
// those two solutions are returned: ((a*c-b*d),(a*d+b*c)) and ((a*c+b*d),(a*d-b*c))
// solutions are sorted, pairs are sorted and numbers are non-negative
fun brahmaguptaFibonacci(ab: Complexl, cd: Complexl): Pair<Complexl, Complexl> {
	val (a, b) = ab
	val (c, d) = cd
	return Pair(Complexl((a * c - b * d).abs(), a * d + b * c).sorted(),
			Complexl(a * c + b * d, (a * d - b * c).abs()).sorted()).sorted()
}

fun PrimeFactorization.computeAllSumOfSquaresRepresentations(): ArrayList<Complexl> {
	val h = HashSet<Complexl>()
	for ((x, y) in this.computeSumOfSquaresRepresentationsIgnoringOrderAndSigns()) {
		h.add(Complexl(x, y))
		h.add(Complexl(-x, y))
		h.add(Complexl(x, -y))
		h.add(Complexl(-x, -y))
		h.add(Complexl(y, x))
		h.add(Complexl(-y, x))
		h.add(Complexl(y, -x))
		h.add(Complexl(-y, -x))
	}
	val al = h.toal()
	al.sort()
	return al
}

fun PrimeFactorization.computeSumOfSquaresRepresentationsIgnoringOrderAndSigns(): ArrayList<Complexl> {
	var res = hashSetOf(Complexl(0, 1))
	var base = 1L
	for ((p, e) in this) {
		if (p % 4 == 3L) {
			if (e.odd())
				return ArrayList()
			base *= p.pow(e / 2)
			continue
		}
		val ab = computeSumsOfSquaresForPrime(p)
		for (_i in 1..e) {
			val h = HashSet<Complexl>()
			for (xy in res) {
				val (solutionA, solutionB) = brahmaguptaFibonacci(ab, xy)
				h.add(solutionA.sorted())
				h.add(solutionB.sorted())
			}
			res = h
		}
	}
	return Complexl(0, base).let { complexBase -> res.map { brahmaguptaFibonacci(it, complexBase).first } }.
			toArrayList().sorted().toArrayList()
}

// counts integer solutions to x*x+y*y = this
fun PrimeFactorization.countAllSumOfSquaresRepresentations(): Long {
	var b = 1L
	for ((p, e) in this) {
		if (p == 2L)
			continue
		if (p % 4 == 3L) {
			if (e.odd())
				return 0L
		} else {
			b *= (e + 1)
		}
	}
	return b * 4
}

// counts distinct integer solutions to x*x+y*y = this with 0 <= x <= y
fun PrimeFactorization.countSumOfSquaresRepresentationsIgnoringOrderAndSigns(): Long {
	if (this.size == 0)
		return 1
	var b = 1L
	var twoCount = 0
	for ((p, e) in this) {
		if (p == 2L) {
			twoCount = e
			continue
		}
		if (p % 4 == 3L) {
			if (e.odd())
				return 0L
		} else {
			b *= (e + 1)
		}
	}
	if (b.even())
		return b / 2
	if (twoCount.even())
		return (b - 1) / 2
	return (b + 1) / 2
}

private val _SUMS_OF_SQUARES_FOR_PRIME = HashMap<Long, Complexl>()
fun computeSumsOfSquaresForPrime(p: Long): Complexl {
	if (p == 2L)
		return Complexl(1, 1)
	astTrue(p.isPrime(), "p ($p) must be prime")
	astEqual(p % 4, 1L, "p ($p) must be 2 or equal to 1 (mod 4)")
	if (p > IMAX) {
		return computeSumsOfSquaresForPrimeFullLong(p)
	}
	if (p in _SUMS_OF_SQUARES_FOR_PRIME)
		return _SUMS_OF_SQUARES_FOR_PRIME[p]!!
	fun mods(_a: Long, n: Long): Long {
		astGreater(n, 0L)
		var a = _a % n
		if (2 * a > n)
			a -= n
		return a
	}

	fun powmods(_a: Long, _r: Long, n: Long): Long {
		var out = 1L
		var r = _r
		var a = _a
		while (r > 0) {
			if (r % 2 == 1L) {
				r -= 1
				out = mods(out * a, n)
			}
			r /= 2
			a = mods(a * a, n)
		}
		return out
	}

	fun quos(a: Long, n: Long): Long {
		astGreater(n, 0L)
		return (a - mods(a, n)) / n
	}

	fun grem(w: Complexl, z: Complexl): Complexl {
		// remainder in Gaussian integers when dividing w by z
		val (w0, w1) = w
		val (z0, z1) = z
		val n = z0 * z0 + z1 * z1
		astUnequal(n, 0L)
		val u0 = quos(w0 * z0 + w1 * z1, n)
		val u1 = quos(w1 * z0 - w0 * z1, n)
		return Complexl(w0 - z0 * u0 + z1 * u1, w1 - z0 * u1 - z1 * u0)
	}

	fun ggcd(w: Complexl, z: Complexl): Complexl {
		var (vw, vz) = Pair(w, z)
		while (vz != Complexl(0, 0)) {
			val tmpvz = vz
			vz = grem(vw, vz)
			vw = tmpvz
		}
		return vw
	}

	fun root4(p: Long): Long {
		// 4th root of 1 modulo p
		astGreater(p, 2L)
		astEqual(p % 4, 1L)
		val k = p / 4
		var j = 2L
		while (true) {
			val a = powmods(j, k, p)
			val b = mods(a * a, p)
			if (b == -1L)
				return a
			astEqual(b, 1L)
			j += 1
		}
	}

	val a = root4(p)
	val solution = ggcd(Complexl(p, 0), Complexl(a, 1))
	val res = Complexl(solution.a.abs(), solution.b.abs())
	_SUMS_OF_SQUARES_FOR_PRIME[p] = res
	return res
}

fun computeSumsOfSquaresForPrimeFullLong(p: Long): Complexl {
	if (p == 2L)
		return Complexl(1, 1)
	astTrue(p.isPrime(), "p ($p) must be prime")
	astEqual(p % 4, 1L, "p ($p) must be 2 or equal to 1 (mod 4)")
	if (p in _SUMS_OF_SQUARES_FOR_PRIME)
		return _SUMS_OF_SQUARES_FOR_PRIME[p]!!
	fun mods(_a: BigInteger, n: Long): Long {
		astGreater(n, 0L)
		var a = (_a % n).toLong()
		if (2 * a > n) // todo: safe?
			a -= n
		return a
	}

	fun powmods(_a: Long, _r: Long, n: Long): Long {
		var out = 1L
		var r = _r
		var a = _a
		while (r > 0) {
			if (r % 2 == 1L) {
				r -= 1
				out = mods(out.big * a, n)
			}
			r /= 2
			a = mods(a.big * a, n)
		}
		return out
	}

	fun quos(a: BigInteger, n: Long): Long {
		astGreater(n, 0L)
		return ((a - mods(a, n)) / n).toLong()
	}

	fun grem(w: Complexl, z: Complexl): Complexl {
		// remainder in Gaussian integers when dividing w by z
		val (w0, w1) = w
		val (z0, z1) = z
		val n = z0 * z0 + z1 * z1
		astUnequal(n, 0L)
		val u0 = quos(w0.big * z0 + w1 * z1, n)
		val u1 = quos(w1.big * z0 - w0 * z1, n)
		return Complexl((w0.big - z0.big * u0 + z1.big * u1).toLong(),
				(w1.big - z0.big * u1 - z1.big * u0).toLong())
	}

	fun ggcd(w: Complexl, z: Complexl): Complexl {
		var (vw, vz) = Pair(w, z)
		while (vz != Complexl(0, 0)) {
			val tmpvz = vz
			vz = grem(vw, vz)
			vw = tmpvz
		}
		return vw
	}

	fun root4(p: Long): Long {
		// 4th root of 1 modulo p
		astGreater(p, 2L)
		astEqual(p % 4, 1L)
		val k = p / 4
		var j = 2L
		while (true) {
			val a = powmods(j, k, p)
			val b = mods(a.big * a, p)
			if (b == -1L)
				return a
			astEqual(b, 1L)
			j += 1
		}
	}

	val a = root4(p)
	val solution = ggcd(Complexl(p, 0), Complexl(a, 1))
	val res = Complexl(solution.a.abs(), solution.b.abs())
	_SUMS_OF_SQUARES_FOR_PRIME[p] = res
	return res
}
