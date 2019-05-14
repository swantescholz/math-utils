package math

import extensions.toArrayList
import math.combinatoric.exactBigIntegerSubsetSum
import org.junit.Test
import util.astEqual
import util.astIn
import util.extensions.big
import java.util.*

class SetsTest {


	@Test
	fun testSubsetSumWorksWithDuplicateElements() {
		val numbers = listOf(1, 1, 1, 2, 2).map { it.big }.toArrayList()
		val solutions = exactBigIntegerSubsetSum(numbers, 4.big)
		astEqual(solutions.size, 7)
		fun createBitSet(vararg bits: Int): BitSet {
			val bs = BitSet()
			for (bit in bits)
				bs.set(bit)
			return bs
		}
		astIn(createBitSet(0, 1, 3), solutions)
		astIn(createBitSet(0, 1, 4), solutions)
		astIn(createBitSet(0, 2, 3), solutions)
		astIn(createBitSet(0, 2, 4), solutions)
		astIn(createBitSet(1, 2, 3), solutions)
		astIn(createBitSet(1, 2, 4), solutions)
	}

}