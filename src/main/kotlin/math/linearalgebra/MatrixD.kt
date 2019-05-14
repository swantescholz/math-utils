package math.linearalgebra

import extensions.even
import extensions.sequences.seq
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.LUDecomposition
import util.astEqual
import util.astGreaterEqual
import util.astTrue
import util.range

// indexed (x,y)
class MatrixD(val width: Int, val height: Int = width, initFunction: (Int, Int) -> Double = { x, y -> 0.0 }) {
	
	val data = Array(width, { x -> Array(height, { y -> initFunction(x, y) }) })
	
	constructor(you: MatrixD) : this(you.width, you.height, { x, y -> you[x, y] })
	
	operator fun get(x: Int, y: Int) = data[x][y]
	
	operator fun get(xyPair: Pair<Int, Int>) = data[xyPair.first][xyPair.second]
	
	operator fun set(x: Int, y: Int, value: Double) {
		data[x][y] = value
	}
	
	operator fun set(xyPair: Pair<Int, Int>, value: Double) {
		data[xyPair.first][xyPair.second] = value
	}
	
	
	operator fun unaryMinus() = MatrixD(width, height, { x, y -> -this[x, y] })
	operator fun plus(you: MatrixD) = MatrixD(width, height, { x, y -> this[x, y] + you[x, y] })
	operator fun minus(you: MatrixD) = MatrixD(width, height, { x, y -> this[x, y] - you[x, y] })
	operator fun times(you: Double) = MatrixD(width, height, { x, y -> this[x, y] * you })
	operator fun mod(you: Double) = MatrixD(width, height, { x, y -> this[x, y] % you })
	operator fun div(you: Double) = this * (1 / you)
	operator fun times(you: MatrixD): MatrixD {
		astTrue(this.width == you.height)
		return MatrixD(you.width, this.height, { x, y ->
			0.seq(width - 1).map {
				this[it, y] * you[x, it]
			}.sum()
		})
	}
	
	operator fun times(you: VectorD): VectorD {
		astTrue(this.width == you.size)
		return VectorD(this.height, { y ->
			0.seq(width - 1).map { x ->
				this[x, y] * you[x]
			}.sum()
		})
	}
	
	fun modPow(exponent: Long, m: Double = Double.MAX_VALUE): MatrixD {
		astGreaterEqual(exponent, 0L)
		astEqual(width, height)
		var y = MatrixD.identity(width)
		if (exponent == 0L) {
			return y
		}
		var x = this
		var n = exponent
		while (n > 1) {
			if (n.even()) {
				x *= x
				n /= 2
			} else {
				y = x * y
				y %= m
				x *= x
				n = (n - 1) / 2
			}
			x %= m
		}
		return (x * y) % m
	}
	
	override fun toString(): String {
		var text = ""
		for (y in 0..height - 1) {
			var row = ""
			for (x in 0..width - 1) {
				row += this[x, y].toString() + " "
			}
			text += row.removeSuffix(" ") + "\n"
		}
		return text.removeSuffix("\n")
	}
	
	fun asSequence(): Sequence<Triple<Int, Int, Double>> {
		return 0.seq(height - 1).map { y ->
			0.seq(width - 1).map { x ->
				Triple(x, y, this[x, y])
			}
		}.flatten()
	}
	
	companion object {
		fun identity(width: Int): MatrixD {
			return MatrixD(width, width, { x, y ->
				if (x == y)
					return@MatrixD 1.0
				0.0
			})
		}
		
		fun diagonal(diagonal: VectorD): MatrixD {
			return MatrixD(diagonal.size, diagonal.size, { x, y ->
				if (x == y) diagonal[x] else 0.0
			})
		}
	}
	
	fun sum(): Double {
		var sum = 0.0
		for (x in range(width)) {
			for (y in range(height)) {
				sum += this[x, y]
			}
		}
		return sum
	}
	
	inline fun update(transformation: (Int, Int, Double) -> Double) {
		for (x in 0..width - 1) {
			for (y in 0..height - 1) {
				this[x, y] = transformation(x, y, this[x, y])
			}
		}
	}
	
	fun solveLinearEquation(rhs: VectorD): VectorD {
		astTrue(width == height)
		astTrue(height == rhs.size)
		val matrix = toArray2DRowRealMatrix()
		val constants = rhs.toArrayRealVector()
		val solver = LUDecomposition(matrix).solver
		val solution = solver.solve(constants)
		return VectorD(solution)
	}
	
	fun toArray2DRowRealMatrix(): Array2DRowRealMatrix {
		val matrix = Array2DRowRealMatrix(width, width)
		for (x in 0..width - 1) {
			for (y in 0..width - 1) {
				matrix.setEntry(y, x, this[x, y])
			}
		}
		return matrix
	}
	
	
}

operator fun Double.times(you: MatrixD) = MatrixD(you.width, you.height, { x, y -> you[x, y] * this })
