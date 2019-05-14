package math.digits

import java.util.*

// returns the indices (bits) needed to be flipped in each step
fun grayCodeBitFlips(numberOfBits: Int): ArrayList<Int> {
	if (numberOfBits == 0)
		return ArrayList()
	val res = arrayListOf(0)
	for (i in 1..numberOfBits - 1) {
		val oldSize = res.size
		res.add(i)
		for (j in 1..oldSize)
			res.add(res[oldSize - j])
	}
	return res
}