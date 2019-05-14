package math.combinatoric

import extensions.sequences.takeUntilNull
import math.sequences.infSequence
import java.util.*

//returned array lists are frozen
fun seqOrderedIndices(numIndices: Int, length: Int): Sequence<ArrayList<Int>> {
	val al = ArrayList<Int>()
	for (it in 0..numIndices - 1)
		al.add(it)
	al[numIndices - 1]--
	return infSequence().map {
		if (al[0] == length - numIndices)
			return@map null
		var index = numIndices - 1
		while (al[index] >= length - numIndices + index) {
			index--
		}
		al[index]++
		for (i in index + 1..numIndices - 1) {
			al[i] = al[i - 1] + 1
		}
		return@map al
	}.takeUntilNull()
}