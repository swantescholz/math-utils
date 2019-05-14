package string

import java.util.*


val ROMAN_VALUES = hashMapOf(Pair('I', 1), Pair('V', 5), Pair('X', 10), Pair('L', 50),
		Pair('C', 100), Pair('D', 500), Pair('M', 1000))
val ROMAN_ENDINGS9 = arrayListOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")
val ROMAN_ENDINGS99 = arrayListOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
val ROMAN_ENDINGS999 = arrayListOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")

fun String.readRoman(): Int {
	var long = this.map { ROMAN_VALUES[it] ?: -1 }.toCollection(arrayListOf<Int>())
	var short = ArrayList<Int>()
	var i = 0
	while (i <= long.size - 1) {
		var x = long[i]
		while (i <= long.size - 2 && long[i] == long[i + 1]) {
			i++
			x += long[i]
		}
		short.add(x)
		i++
	}
	var shorter = ArrayList<Int>()
	i = 0
	while (i <= short.size - 1) {
		if (i <= short.size - 2 && short[i] < short[i + 1]) {
			shorter.add(short[i + 1] - short[i])
			i++
		} else {
			shorter.add(short[i])
		}
		i++
	}
	return shorter.sum()
}


fun Int.toRoman(): String {
	val end = ROMAN_ENDINGS9[this % 10]
	val start = "M".repeat(this / 1000)
	val n = (this % 1000) / 10
	return start + ROMAN_ENDINGS999[n / 10] + ROMAN_ENDINGS99[n % 10] + end
}