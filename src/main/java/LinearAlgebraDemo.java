import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class LinearAlgebraDemo {
	public static void main(String[] args) {


		RealMatrix coefficients =
				new Array2DRowRealMatrix(new double[][]{{2, 3, -2}, {-1, 7, 6}, {4, -3, -5}},
						false);
		DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

		RealVector constants = new ArrayRealVector(new double[]{1, -2, 1}, false);
		RealVector solution = solver.solve(constants);
		System.out.println(solution);
	}
}
