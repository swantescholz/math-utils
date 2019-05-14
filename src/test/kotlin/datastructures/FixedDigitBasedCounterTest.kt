package datastructures

import math.digits.FixedDigitBasedCounter
import org.junit.Assert
import org.junit.Test
import util.range
import java.util.*


class FixedDigitBasedCounterTest {

	@Test
	fun testFixedDigitBasedCounter() {
		val a = FixedDigitBasedCounter(0, { 0 })
		Assert.assertFalse(a.done())
		a.increment()
		Assert.assertTrue(a.done())
		Assert.assertEquals(a.digits, ArrayList<Int>())
		val b = FixedDigitBasedCounter(3, { it + 1 })
		b.increment()
		Assert.assertFalse(b.done())
		Assert.assertEquals(b.digits, arrayListOf(0, 0, 0))
		for (i in range(3)) {
			b.increment()
		}
		Assert.assertEquals(b.digits, arrayListOf(0, 1, 1))
		Assert.assertFalse(b.done())
		b.increment()
		Assert.assertFalse(b.done())
		b.increment()
		Assert.assertTrue(!b.done())
		b.increment()
		Assert.assertTrue(b.done())
		Assert.assertEquals(b.countPossibleNumbers(), 6)
	}

}