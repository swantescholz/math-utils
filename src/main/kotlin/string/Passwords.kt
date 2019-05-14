package string

import extensions.sequences.seq
import math.digits.FixedDigitBasedCounter
import java.util.*

fun <T> generatePasswordsOfLength(length: Int, alphabet: ArrayList<T>): Sequence<ArrayList<T>> {
	val asize = alphabet.size
	val dbc = FixedDigitBasedCounter(length, { asize })
	return 1L.seq(dbc.countPossibleNumbers()).map {
		dbc.increment()
		dbc.digits.asSequence().map { alphabet[it] }.toCollection(arrayListOf<T>())
	}
}

fun <T> generatePasswordsWithMaxLength(maxLength: Int, alphabet: ArrayList<T>): Sequence<ArrayList<T>> {
	return 1.seq(maxLength).map { generatePasswordsOfLength(length = it, alphabet = alphabet) }.flatten()
}