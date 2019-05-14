package math

import math.equations.hasLinearDiophantineEquationPositiveSolution
import org.junit.Test
import util.astFalse
import util.astTrue

class EquationsTest {
	
	@Test
	fun testLinearDiophantine() {
		astFalse(hasLinearDiophantineEquationPositiveSolution(10, 2, 4))
		astTrue(hasLinearDiophantineEquationPositiveSolution(10, 2, 14))
		astTrue(hasLinearDiophantineEquationPositiveSolution(10, -2, -14))
		astFalse(hasLinearDiophantineEquationPositiveSolution(-10, -2, -4))
		astTrue(hasLinearDiophantineEquationPositiveSolution(-10, -2, -14))
	}
	
}