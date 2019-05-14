package math.primes

import algorithms.binaryFindLargest
import extensions.floorSqrt
import extensions.sequences.seq
import util.HOME
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigInteger
import java.util.*


var PRIMES = arrayListOf(2L, 3L, 5L, 7L)
var PRIME_SET = HashSet(PRIMES)

fun Long.isPrime(): Boolean {
	if (this <= PRIMES.last() && PRIMES.size == PRIME_SET.size)
		return this in PRIME_SET
	return BigInteger.valueOf(this).isProbablePrime(50)
}

fun Int.isPrime(): Boolean {
	return this.toLong().isPrime()
}

fun seqPrimes(limit: Int): Sequence<Int> {
	val bs = BitSet(limit)
	bs.set(0, limit)
	bs.clear(0)
	bs.clear(1)
	val maxSqrt = Math.sqrt(limit.toDouble()).toInt()
	for (i in 2..maxSqrt) {
		if (bs[i]) {
			var m = i * i
			while (m < limit) {
				bs.clear(m)
				m += i
			}
		}
	}
	val primeCount = bs.cardinality()
	var i = -1
	return 1.seq(primeCount).map {
		i = bs.nextSetBit(i + 1)
		i
	}
}

fun countPrimesUpTo(maxIncluding: Long): Long {
	if (maxIncluding <= PRIMES.last()) {
		val index = PRIMES.binaryFindLargest { it <= maxIncluding }
		return index + 1L
	}
	if (maxIncluding > 1000000000) {
		val process = ProcessBuilder("${HOME}/mybin/primecount/primecount", "$maxIncluding").start()
		val reader = BufferedReader(InputStreamReader(process.inputStream))
		return reader.readLine().toLong()
	}
	var sum = 0L
	val np = countPrimesUpTo(maxIncluding.floorSqrt()).toInt()
	val p = IntArray(np.toInt() + 1, { PRIMES[it].toInt() })
	p[p.size - 1] = 0

	fun phi(x: Long, _a: Int, sign: Int) {
		var a = _a
		while (true) {
			if (a == 0) {
				sum += sign * x
				break
			} else if (x < p[a]) {
				sum += sign
				break
			} else {
				--a
				phi(x / p[a], a, -sign)
			}
		}
	}
	sum = np - 1L
	phi(maxIncluding, np, 1)
	return sum
}