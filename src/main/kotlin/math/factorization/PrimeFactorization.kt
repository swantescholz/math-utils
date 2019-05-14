package math.factorization

import extensions.*
import extensions.sequences.ass
import extensions.sequences.lproduct
import extensions.sequences.product
import extensions.sequences.seq
import math.BIG0
import math.BIG1
import math.BIG2
import math.LMAX
import math.primes.PRIMES
import math.primes.isPrime
import util.*
import util.extensions.big
import java.math.BigInteger
import java.util.*

class PrimeFactorization(val primeFactors: ArrayList<Long>,
                         val exponents: ArrayList<Int>) {
	
	init {
		astEqual(primeFactors.size, exponents.size)
	}
	
	constructor(primesDivisors: ArrayList<Long>) :
	this(primesDivisors, 1.seq(primesDivisors.size).map { 1 }.toArrayList())
	
	val size: Int
		get() = primeFactors.size
	val product: Long
		get() = 0.seq(primeFactors.size - 1).map { primeFactors[it].pow(exponents[it]) }.product()
	val bigProduct: BigInteger
		get() = 0.seq(primeFactors.size - 1).map { primeFactors[it].big.pow(exponents[it]) }.product()
	
	constructor(primesOccurrences: TreeMap<Long, Int>) :
	this(primesOccurrences.keys.toArrayList(), primesOccurrences.values.toArrayList())
	
	override fun toString(): String {
		if (size == 0)
			return "1"
		return range(primeFactors).map { "${primeFactors[it]}${if (exponents[it] > 1) "^${exponents[it]}" else ""}" }.
				joinToString(separator = " * ")
	}
	
	operator fun iterator(): Iterator<Pair<Long, Int>> {
		class MyIterator : Iterator<Pair<Long, Int>> {
			var currentIndex = 0
			override fun next(): Pair<Long, Int> {
				val res = Pair(primeFactors[currentIndex], exponents[currentIndex])
				currentIndex++
				return res
			}
			
			override fun hasNext(): Boolean = currentIndex < primeFactors.size
		}
		return MyIterator()
	}
	
	fun divisorFunction(sigmaIndexX: Int): BigInteger {
		astGreaterEqual(sigmaIndexX, 2)
		if (size == 0)
			return BIG1
		return 0.seq(size - 1).map {
			val p = primeFactors[it].big
			val e = exponents[it]
			val pPowX = p.pow(sigmaIndexX)
			return@map (pPowX.pow(e + 1) - 1) / (pPowX - 1)
		}.product()
	}
	
	fun countDivisors(): Long {
		if (size == 0)
			return 1
		return 0.seq(size - 1).map { exponents[it] + 1 }.lproduct()
	}
	
	fun copy(): PrimeFactorization = PrimeFactorization(ArrayList(primeFactors), ArrayList(exponents))
	
	operator fun get(index: Int): Pair<Long, Int> {
		return Pair(primeFactors[index], exponents[index])
	}
	
	operator fun times(other: PrimeFactorization): PrimeFactorization {
		val a = ArrayList<Long>()
		val b = ArrayList<Int>()
		var i = 0
		var j = 0
		while (i < size || j < other.size) {
			if (i == size) {
				for (k in j..other.size - 1) {
					a.add(other.primeFactors[k])
					b.add(other.exponents[k])
				}
				break
			} else if (j == other.size) {
				for (k in i..size - 1) {
					a.add(primeFactors[k])
					b.add(exponents[k])
				}
				break
			} else {
				if (primeFactors[i] < other.primeFactors[j]) {
					a.add(primeFactors[i])
					b.add(exponents[i])
					i++
				} else if (primeFactors[i] > other.primeFactors[j]) {
					a.add(other.primeFactors[j])
					b.add(other.exponents[j])
					j++
				} else {
					a.add(primeFactors[i])
					b.add(exponents[i] + other.exponents[j])
					i++
					j++
				}
			}
		}
		return PrimeFactorization(a, b)
	}
	
	fun isPrime() = size == 1
	
	companion object {
		val ONE = PrimeFactorization(ArrayList(), ArrayList())
		
		fun computeMaxPrimeFactorOf(n: Long): Long {
			if (n <= 1)
				return 1
			var x = n
			for (p in PRIMES) {
				while (x % p == 0L) {
					x /= p
				}
				if (x == 1L)
					return p
				if (p * p > x)
					return x
			}
			astFail("not enough primes (up to ${PRIMES.last()}) to factor $n")
			null!!
		}
		
		fun fromPrime(prime: Long) = PrimeFactorization(arrayListOf(prime), arrayListOf(1))
		
		fun factorize(numberToFactorize: Int) = factorize(numberToFactorize.toLong())
		fun factorize(numberToFactorize: Long): PrimeFactorization {
			var n = numberToFactorize
			val factorization = PrimeFactorization(ArrayList(), ArrayList())
			if (n < 2)
				return factorization
			if (n.isPrime())
				return PrimeFactorization(arrayListOf(n), arrayListOf(1))
			for (p in PRIMES) {
				var count = 0
				while (n % p == 0L) {
					n /= p
					count++
				}
				if (count > 0) {
					factorization.primeFactors.add(p)
					factorization.exponents.add(count)
				}
				if (n == 1L || p * p > n)
					break
			}
			if (n != 1L) {
				if (n > PRIMES.last().square())
					throw IllegalArgumentException("not enough primes for factoring $numberToFactorize")
				factorization.primeFactors.add(n)
				factorization.exponents.add(1)
			}
			return factorization
		}
		
		fun factorize(numberToFactorize: BigInteger): PrimeFactorization {
			var n = numberToFactorize
			val factorization = PrimeFactorization(ArrayList(), ArrayList())
			if (n < BIG2)
				return factorization
			if (n.isProbablePrime(50)) {
				if (n > Long.MAX_VALUE.big)
					throw IllegalArgumentException("$numberToFactorize is too big for long factorization")
				return PrimeFactorization(arrayListOf(n.toLong()), arrayListOf(1))
			}
			for (p in PRIMES) {
				var count = 0
				while (n.mod(p.big) == BIG0) {
					n /= p
					count++
				}
				if (count > 0) {
					factorization.primeFactors.add(p)
					factorization.exponents.add(count)
				}
				if (n == BIG1 || p.big * p > n)
					break
			}
			if (n != BIG1) {
				if (n > PRIMES.last().square().big)
					throw IllegalArgumentException("not enough primes for factoring $numberToFactorize")
				factorization.primeFactors.add(n.toLong())
				factorization.exponents.add(1)
			}
			return factorization
		}
	}
	
	fun pow(exponent: Int): PrimeFactorization {
		return PrimeFactorization(ArrayList(primeFactors), exponents.ass().map { it * exponent }.toal())
	}
	
	fun computeUnitaryDivisors(): ArrayList<Long> {
		astLessEqual(product, LMAX, "unitary divisors don't fit into long")
		val res = ArrayList<Long>()
		fun fr(currentProduct: Long, primeIndex: Int) {
			res.add(currentProduct)
			for (index in primeIndex + 1..primeFactors.size - 1) {
				fr(currentProduct * primeFactors[index].pow(exponents[index]), index)
			}
		}
		fr(1L, -1)
		res.sort()
		return res
	}
	
	fun gdc(o: PrimeFactorization): PrimeFactorization {
		val f = ArrayList<Long>()
		val e = ArrayList<Int>()
		var (a, b) = Pair(0, 0)
		while (true) {
			if (a >= size || b >= o.size)
				break
			(primeFactors[a] - o.primeFactors[b]).let { diff ->
				when {
					diff < 0 -> a++
					diff > 0 -> b++
					else -> {
						f.add(primeFactors[a])
						e.add(exponents[a].min(o.exponents[b]))
						a++
						b++
					}
				}
			}
		}
		return PrimeFactorization(f, e)
	}
	
	fun lcm(o: PrimeFactorization): PrimeFactorization {
		val f = ArrayList<Long>()
		val e = ArrayList<Int>()
		var (a, b) = Pair(0, 0)
		while (true) {
			if (a >= size && b >= o.size)
				break
			val diff = if (a >= size) {
				1
			} else if (b >= o.size) {
				-1
			} else {
				primeFactors[a] - o.primeFactors[b]
			}
			when {
				diff < 0 -> {
					f.add(primeFactors[a])
					e.add(exponents[a])
					a++
				}
				diff > 0 -> {
					f.add(o.primeFactors[b])
					e.add(o.exponents[b])
					b++
				}
				else -> {
					f.add(primeFactors[a])
					e.add(exponents[a].max(o.exponents[b]))
					a++
					b++
				}
			}
		}
		return PrimeFactorization(f, e)
	}
	
	
}