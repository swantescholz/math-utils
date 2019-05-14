package math

import math.linearalgebra.MatrixC
import math.linearalgebra.MatrixD
import math.linearalgebra.VectorC
import math.linearalgebra.VectorD
import org.apache.commons.math3.complex.Complex
import org.junit.Test
import util.astAlmostEqual

class MatrixTest {
	
	
	@Test
	fun testSolving() {
		val ma = MatrixD(2, 2, { x, y -> y * 2.0 + x + 1 })
		val va = VectorD(arrayListOf(17.0, 39.0))
		val xa = ma.solveLinearEquation(va)
		astAlmostEqual(xa[0], 5.0)
		astAlmostEqual(xa[1], 6.0)
		val mb = MatrixC(2, 2)
		mb[0, 0] = Complex(1.0, 1.0)
		mb[1, 0] = Complex(2.0, -1.0)
		mb[0, 1] = Complex(3.0, 2.0)
		mb[1, 1] = Complex(4.0, -3.0)
		val vb = VectorC(2)
		vb[0] = Complex(21.0, 3.0)
		vb[1] = Complex(48.0, -2.0)
		val xb = mb.solveLinearEquation(vb)
		astAlmostEqual(xb[0].real, 1.0)
		astAlmostEqual(xb[1].real, 7.0)
		astAlmostEqual(xb[0].imaginary, -1.0)
		astAlmostEqual(xb[1].imaginary, 5.0)
		
	}
	
}