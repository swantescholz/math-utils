package algorithms

import datastructures.DynamicPriorityQueue
import extensions.almostEqual
import math.linearalgebra.VectorD
import org.junit.Assert
import org.junit.Test

class GradientDescentTest {
	
	@Test
	fun testGradientDescent() {
		val (a) = gradientDescent(VectorD(5.0), 0.1, {
			val (x) = it;
			VectorD(4 * x*x*x - 9 * x*x)
		}, tolerance = 1e-7)
		Assert.assertEquals(a, 9/4.0, 1e-7)
		val (x,y) = gradientDescent(VectorD(5.0, 5.0), 0.1, {
			val (x,y) = it;
			VectorD(200*(x-y*y), 2*(-1+y-200*x*y+200*y*y*y))
		}, tolerance = 1e-7)
		Assert.assertEquals(x, 1.0, 1e-7)
		Assert.assertEquals(y, 1.0, 1e-7)
	}
	
}