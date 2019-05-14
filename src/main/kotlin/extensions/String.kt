package extensions

import string.ALPHABET_SMALL
import string.LETTER_FREQUENCIES
import string.READABLE_CHARS
import java.util.*

infix operator fun Int.times(stringToRepeat: String) = stringToRepeat.repeat(this)
infix operator fun Long.times(stringToRepeat: String) = stringToRepeat.repeat(this.toInt())
infix operator fun String.times(numberOfRepetitions: Int) = repeat(numberOfRepetitions)

fun String.asciiSum(): Int {
	return this.map { it.toInt() }.sum()
}


fun String.trimQuotes(): String {
	if (this.length <= 1)
		return "" + this
	if (this[0] == this[this.length - 1]) {
		if (this[0] == '\'' || this[0] == '\"')
			return this.substring(1, this.length - 1)
	}
	return "" + this
}

fun Char.getLetterValue(): Int {
	val ich = this.toInt()
	if (ich >= 65 && ich <= 90) return ich - 64
	if (ich >= 97 && ich <= 122) return ich - 96
	throw IllegalArgumentException("bad letter value")
}

fun String.getWordValue(): Long {
	var sum = 0L
	for (c in this) {
		sum += c.getLetterValue()
	}
	return sum
}

fun String.isPalindrome(): Boolean {
	return this == this.reversed()
}

fun Char.getDigitValue(): Int {
	val ich = this.toByte()
	if (ich >= 48 && ich <= 57) return ich - 48
	return 0
}

fun String.getDigitSum(): Int {
	return this.map { it.getDigitValue() }.sum()
}

fun String.diffToExpectedFrequencies(): Double {
	if (this.any { it !in READABLE_CHARS })
		return Double.MAX_VALUE
	var s = this.toLowerCase()
	var hm = HashMap<Char, Int>()
	s.filter { it in ALPHABET_SMALL }.groupBy { it }.map { Pair(it.key, it.value.size) }
			.forEach { hm.put(it.first, it.second) }
	ALPHABET_SMALL.filter { it !in hm }.forEach { hm.put(it, 0) }
	var count = hm.asSequence().sumBy { it.value }
	var diffs = hm.asSequence().map {
		Pair(it.key,
				(LETTER_FREQUENCIES[it.key] ?: 0.0) diff it.value.toDouble() / count)
	}.toMap()
	return diffs.map { it.value }.sum()
}