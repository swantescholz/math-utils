package math.sequences

import extensions.modMul
import extensions.odd
import math.BIG0
import math.BIG1
import java.math.BigInteger

fun seqFibonacci(): Sequence<BigInteger> {
	var a = BIG0
	var b = BIG1
	return infSequence().map {
		val tmp = a
		a = b
		b = tmp + b
		return@map tmp
	}
}


fun computeNthFibonacciNumberMod(n: Long, mod: Long = Long.MAX_VALUE): Long {
	//	f(2 * k) = f(k) * f(k) + f(k - 1) * f(k - 1)
	//	f(2 * k + 1) = f(k) * f(k + 1) + f(k - 1) * f(k)
	val mem = hashMapOf(0L to 0L, 1L to 1L)
	fun f(k: Long): Long {
		if (k in mem)
			return mem[k]!!
		var res: Long
		if (k.odd()) {
			res = f(k / 2).let { it.modMul(it, mod) } + f(k / 2 + 1).let { it.modMul(it, mod) }
		} else {
			res = f(k / 2).let { it.modMul(it + 2 * f(k / 2 - 1), mod) }
		}
		res %= mod
		mem[k] = res
		return res
	}
	return f(n)
}
