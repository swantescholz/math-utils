package math.digits

import extensions.pow
import extensions.sequences.prepend
import extensions.sequences.seq
import extensions.sequences.takeUntilNull
import math.BIG0
import math.DIGITS_10al
import java.math.BigInteger
import java.util.*


fun Long.getNthDigit(nth: Int, radix: Long = 10): Int {
	val pow = radix.pow(nth.toLong())
	return ((this % (pow * radix)) / pow).toInt()
}

fun bcdCounter(length: Int, radix: Byte = 10): Sequence<Array<Byte>> {
	val arr = Array<Byte>(length, { 0 })
	arr[0]--
	return 1L.seq(radix.toLong().pow(length.toLong())).map {
		arr[0]++
		for (i in 0..length - 2) {
			if (arr[i] == radix) {
				arr[i] = 0
				arr[i + 1]++
			}
		}
		return@map arr
	}
}

fun BigInteger.getDigitSum(radix: Int = 10): Long {
	val r = BigInteger.valueOf(radix.toLong())
	var bi = this
	var sum = 0L
	while (bi.compareTo(BIG0) > 0) {
		sum += bi.mod(r).toLong()
		bi = bi.divide(r)
	}
	return sum
}

fun Long.getDigitSum(base: Long = 10): Int {
	return this.getDigitsReversed(base).sum()
}

fun Long.seqDigitalRoots(): Sequence<Long> {
	var x = this
	return 1.seq().map {
		if (x < 10)
			return@map null
		x = x.getDigitSum().toLong()
		x
	}.takeUntilNull().prepend(this)
}

fun Long.concat(you: Long): Long {
	return this * 10L.pow(you.length(base = 10).toLong()) + you
}

fun Long.length(base: Int = 10): Int {
	if (this == 0L)
		return 1
	if (this < 0L) {
		val tmp = -this
		return tmp.length(base)
	}
	var len = 0
	var nvar = this
	while (nvar > 0) {
		nvar /= base
		len++
	}
	return len
}

fun Long.isPalindrome(base: Long = 10L): Boolean {
	// todo faster
	var digits = this.getDigitsReversed(base)
	var rev = ArrayList(digits)
	rev.reverse()
	return rev == digits
}

fun Long.containsZeros(): Boolean {
	if (this == 0L)
		return true
	var nvar = this
	if (nvar < 0)
		nvar *= -1
	var lastWasZero = false
	while (nvar > 0) {
		if (nvar % 10 == 0L)
			lastWasZero = true
		else if (lastWasZero)
			return true
		nvar /= 10
	}
	return false
}

fun Long.circleNumber(): Long {
	if (this <= 0 || this.containsZeros())
		throw IllegalArgumentException("not positive or is with zeros")
	if (this < 10)
		return this
	var tenPow = 10L
	while (this / tenPow >= 10)
		tenPow *= 10
	return (this % tenPow) * 10L + this / tenPow
}


fun Long.getDigitIndicesReversed(digit: Long, base: Int = 10): ArrayList<Int> {
	if (this < 0)
		throw IllegalArgumentException("should not be negative!")
	if (this < base) {
		if (this == digit)
			return arrayListOf(0)
		return ArrayList()
	}
	var nvar = this
	var index = 0
	val digitIndices = ArrayList<Int>()
	while (nvar > 0) {
		if (nvar % base == digit) {
			digitIndices.add(index)
		}
		nvar /= base
		index++
	}
	return digitIndices
}

fun Iterable<Int>.digitsToLong(radix: Int = 10): Long {
	var n = 0L
	for (digit in this) {
		n *= radix
		n += digit
	}
	return n
}

fun Long.getDigitsReversed(base: Long = 10L): ArrayList<Int> {
	if (this < 0L)
		throw IllegalArgumentException("this number should not be negative")
	val al = ArrayList<Int>()
	var nvar = this
	while (nvar > 0) {
		al.add((nvar % base).toInt())
		nvar /= base
	}
	return al
}

fun Long.countDigit(digit: Long, base: Int = 10): Int {
	if (this < 0)
		return (-this).countDigit(digit, base)
	if (this < base) {
		if (this == digit)
			return 1
		return 0
	}
	var count = 0
	var nvar = this
	while (nvar > 0) {
		if (nvar % base == digit)
			count++
		nvar /= base
	}
	return count
}

fun Long.countDigits(radix: Int = 10): Array<Int> {
	if (this < 0)
		return (-this).countDigits()
	var occurrences = Array(radix, { 0 })
	var n = this
	while (n > 0) {
		occurrences[n.toInt() % radix]++
		n /= radix
	}
	return occurrences
}

fun Long.isPandigital(digitsToUse: ArrayList<Int> = DIGITS_10al): Boolean {
	var digits = this.getDigitsReversed()
	digits.sort()
	return digits == digitsToUse
}

fun Long.isPandigital9(): Boolean {
	if (this < 123456789 || this > 987654321)
		return false
	val hs = Array(9, { false })
	var n = this
	for (i in 1..9) {
		val x = (n % 10).toInt() - 1
		if (x < 0 || hs[x])
			return false
		hs[x] = true
		n /= 10L
	}
	return true
}


