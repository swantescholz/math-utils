package math.modular

import datastructures.ModLong
import extensions.*
import math.BIG0
import math.BIG2
import math.factorization.PrimeFactorization
import math.factorization.gcd
import util.astEqual
import util.extensions.big
import util.range
import java.util.*


// computes 1+x+x^2...+x^n % m
fun geometricSumMod(x: Long, n: Long, m: Long): Long {
	var q = x.big
	var exp = n.big
	var factor = 1L.big
	var sum = 0L.big
	val mbig = m.big
	while (exp > BIG0 && q != BIG0) {
		if (exp % BIG2 == BIG0) {
			val temp = (factor * q.modPow(exp, mbig)) % m
			sum = (sum + temp) % m
			exp -= 1
		}
		factor = (((1 + q) % m) * factor) % m
		q = (q * q) % m
		exp /= 2
	}
	return (((sum + factor) % m + m) % m).toLong()
}

fun solveChineseRemainderFor2ArbitraryIntegers(factorsOfA: PrimeFactorization, factorsOfB: PrimeFactorization,
                                               remainderA: Long, remainderB: Long): Long {
	val rems = ArrayList<Long>()
	val ps = ArrayList<Long>()
	val es = ArrayList<Int>()
	var x = 0
	var y = 0
	while (true) {
		if (x == factorsOfA.size) {
			for (i in y..factorsOfB.size - 1) {
				ps.add(factorsOfB.primeFactors[i])
				es.add(factorsOfB.exponents[i])
				rems.add(remainderB % factorsOfB.primeFactors[i].pow(factorsOfB.exponents[i]))
			}
			break
		} else if (y == factorsOfB.size) {
			for (i in x..factorsOfA.size - 1) {
				ps.add(factorsOfA.primeFactors[i])
				es.add(factorsOfA.exponents[i])
				rems.add(remainderA % factorsOfA.primeFactors[i].pow(factorsOfA.exponents[i]))
			}
			break
		} else {
			if (factorsOfA.primeFactors[x] < factorsOfB.primeFactors[y]) {
				ps.add(factorsOfA.primeFactors[x])
				es.add(factorsOfA.exponents[x])
				rems.add(remainderA % factorsOfA.primeFactors[x].pow(factorsOfA.exponents[x]))
				x++
			} else if (factorsOfA.primeFactors[x] > factorsOfB.primeFactors[y]) {
				ps.add(factorsOfB.primeFactors[y])
				es.add(factorsOfB.exponents[y])
				rems.add(remainderB % factorsOfB.primeFactors[y].pow(factorsOfB.exponents[y]))
				y++
			} else {
				val (ea, eb) = Pair(factorsOfA.exponents[x], factorsOfB.exponents[y])
				val d = factorsOfA.primeFactors[x]
				ps.add(d)
				if (ea < eb) {
					es.add(eb)
					rems.add(remainderB % d.pow(eb))
					if (remainderB % d.pow(ea) != remainderA % d.pow(ea))
						return 0L
				} else {
					es.add(ea)
					rems.add(remainderA % d.pow(ea))
					if (remainderB % d.pow(eb) != remainderA % d.pow(eb))
						return 0L
				}
				x++
				y++
			}
		}
	}
	val pf = PrimeFactorization(ps, es)
	return solveChineseRemainders(pf, rems)
}

// factors of 60, remainders 3,2,1 -> 11
fun solveChineseRemainders(factorsOfN: PrimeFactorization, remainders: ArrayList<Long>): Long {
	astEqual(factorsOfN.size, remainders.size)
	val N = factorsOfN.product
	var res = ModLong(N)
	for (i in range(factorsOfN.size)) {
		val (p, e) = factorsOfN[i]
		val m = p.pow(e)
		val r = remainders[i]
		res += (r * (N / m)) % N * (N / m).modInv(m)
	}
	return res.value
}


// solves ax = b (mod m); smallest solution will be returned (or null).
// other solutions are obtainable by adding m/gcd(a,m) up to gcd(a,m)-1 times
fun solveLinearCongruence(a: Long, b: Long, m: Long): Long? {
	if (a < 0)
		return solveLinearCongruence(-a, -b, m)
	if (a >= m || b >= m)
		return solveLinearCongruence(a % m, b % m, m)
	val d = a.gcd(m)
	if (b % d != 0L)
		return null
	var x0 = euclidExtended(a, m).first
	return (m + (x0 * b / d) % m) % (m / d)
}

//returns (x,y,d), so that ax+by = d = gcd(a,b)
fun euclidExtended(a: Long, b: Long): Triple<Long, Long, Long> {
	var (s, old_s) = Pair(0L, 1L)
	var (t, old_t) = Pair(1L, 0L)
	var (r, old_r) = Pair(b, a)
	while (r != 0L) {
		val quotient = old_r / r
		var tmp = r
		r = old_r - quotient * r
		old_r = tmp
		tmp = s
		s = old_s - quotient * s
		old_s = tmp
		tmp = t
		t = old_t - quotient * t
		old_t = tmp
	}
	return Triple(old_s, old_t, old_r)
}

// returns modular inverse x of this such that this*x % m = 1, m should be p^k with p prime
fun Long.modInv(m: Long): Long {
	val res = euclidExtended(this, m).first
	if (res < 0)
		return res + m
	return res
}

fun Int.modInv(m: Int): Int = this.toLong().modInv(m.toLong()).toInt()