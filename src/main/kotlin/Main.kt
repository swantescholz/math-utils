import algorithms.gradientDescent
import extensions.*
import extensions.sequences.ass
import math.BIG0
import math.NANO
import math.combinatoric.nCr
import math.factorization.gcd
import math.linearalgebra.VectorD
import math.primes.PRIMES
import math.primes.PRIME_SET
import math.primes.seqPrimes
import org.apache.commons.math3.linear.RealMatrix
import org.apache.commons.math3.util.ArithmeticUtils
import string.alof
import string.print
import string.printl
import util.doEveryNTimes
import util.extensions.double
import util.printlnRegularly
import util.quit
import java.util.*
import kotlin.system.measureNanoTime

var res = 0L
val primeMax = 1L * 1000000

// ===========================================
/*
49 0.49000000000000027 10000.0
50 0.5000000000000002 10000.0
378563.2605894933
0
quitting..
elapsed time: 1467.33s = 24m 27s

 */
class X(val n: Int, val p : Double) {
	val fCache = DoubleArray(n+1)
	val gCache = DoubleArray(n+1)
	fun gMin1Infected(n: Int): Double {
		assert(n > 0)
		var res = Double.MAX_VALUE
		val probMin1PositiveOutOfN = 1.0 - Math.pow(1.0 - p, n.double)
		for (i in 1..n - 1) {
			val condProbMin1PositiveOutOfI = (1.0 - Math.pow(1.0 - p, i.double)) / probMin1PositiveOutOfN // conditional prob
			val expected = 1.0 + condProbMin1PositiveOutOfI * (gCache[i] + fCache[n - i]) +
					(1 - condProbMin1PositiveOutOfI) * (gCache[n - i])
			res = res.min(expected)
		}
		gCache[n] = res
		return res
	}
	
	fun fNoInfo(n: Int): Double {
		var res = Double.MAX_VALUE
		for (i in 1..n) {
			val probMin1 = 1.0 - Math.pow(1.0 - p, i.double)
			val tmp = fCache[n - i]
			val expected = 1.0 + probMin1 * (gCache[i] + tmp) + (1 - probMin1) * tmp
			res = res.min(expected)
		}
		fCache[n] = res
		return res
	}
	fun solve(): Double {
		fCache[0] = 0.0
		fCache[1] = 1.0
		gCache[0] = 0.0
		gCache[1] = 0.0
		for (j in 2..n) {
			gMin1Infected(j)
			fNoInfo(j)
		}
		return fCache[n]
	}
}

suspend fun solve(n:Int, p:Double): Double {
	return X(n,p).solve()
}

fun mainb() {
	val n = 10000
	var res = 0.0
	
	async {
	
	}
	
	(1..50).ass().map{ {solve(n,0.01*it)}}
	for (i in 1..50) {
		
		
		val tmp = fCache[n]
		printl(i, tmp)
		p += 0.01
		res += tmp
	}
	printl(res)
}


// ****************************************

fun maininit() {
	print("start", "init...")
	PRIMES = seqPrimes(primeMax.toInt()).map(Int::toLong).toal()
	PRIME_SET = HashSet(PRIMES)
//		SQUARES = genSquareNumbers().take(2000000).toal()
//		SQUARES_SET = HashSet(SQUARES)
	printl("", "done")
}

fun main(args: Array<String>) {
	val s = NANO * measureNanoTime {
		maininit()
		printl("starting", "euler", "...")
		mainb()
	}
	printl(res)
	printl("quitting..")
	println("elapsed time: %.2fs".format(s) + " = ${(s / 60).toInt()}m ${s.toInt() % 60}s")
	BIG0 + 0
}