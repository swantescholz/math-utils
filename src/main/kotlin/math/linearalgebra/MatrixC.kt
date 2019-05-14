package math.linearalgebra

import extensions.*
import extensions.sequences.seq
import org.apache.commons.math3.complex.Complex
import util.astEqual
import util.astTrue

// indexed (x,y)
class MatrixC(val width: Int, val height: Int = width,
              initFunction: (Int, Int) -> Complex = { x, y -> Complex(0.0) }) {
	
	val data = Array(width, { x -> Array(height, { y -> initFunction(x, y) }) })
	
	constructor(you: MatrixC) : this(you.width, you.height, { x, y -> you[x, y] })
	
	operator fun get(x: Int, y: Int) = data[x][y]
	
	operator fun set(x: Int, y: Int, value: Complex) {
		data[x][y] = value
	}
	
	operator fun set(x: Int, y: Int, value: Double) {
		data[x][y] = Complex(value)
	}
	
	
	operator fun unaryMinus() = MatrixC(width, height, { x, y -> -this[x, y] })
	operator fun plus(you: MatrixC) = MatrixC(width, height, { x, y -> this[x, y] + you[x, y] })
	operator fun minus(you: MatrixC) = MatrixC(width, height, { x, y -> this[x, y] - you[x, y] })
	operator fun times(you: Double) = MatrixC(width, height, { x, y -> this[x, y] * you })
	operator fun times(you: Complex) = MatrixC(width, height, { x, y -> this[x, y] * you })
	operator fun div(you: Complex) = this * (1.0 / you)
	operator fun times(you: MatrixC): MatrixC {
		astTrue(this.width == you.height)
		return MatrixC(you.width, this.height, { x, y ->
			0.seq(width - 1).map {
				this[it, y] * you[x, it]
			}.reduce { a, b -> a + b }
		})
	}
	
	operator fun times(you: VectorC): VectorC {
		astTrue(this.width == you.size)
		return VectorC(this.height, { y ->
			0.seq(width - 1).map {
				this[it, y] * you[it]
			}.reduce { a, b -> a + b }
		})
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
	
	// solves this*x = rhs for x
	fun solveLinearEquation(rhs: VectorC): VectorC {
		astEqual(this.width, this.height)
		astEqual(rhs.size, this.height)
		val m = MatrixD(2 * width)
		for (y in 0..width - 1) {
			for (x in 0..width - 1) {
				m[x, y] = this[x, y].real
				m[x + width, y] = -this[x, y].imaginary
				m[x, y + width] = this[x, y].imaginary
				m[x + width, y + width] = this[x, y].real
			}
		}
		val realRhs = VectorD(2 * width, {
			if (it < width)
				return@VectorD rhs[it].real
			return@VectorD rhs[it - width].imaginary
		})
		val realSolutions = m.solveLinearEquation(realRhs)
		return VectorC(width, {
			Complex(realSolutions[it], realSolutions[it + width])
		})
	}
	
	companion object {
		fun identity(width: Int): MatrixC {
			return MatrixC(width, width, { x, y ->
				if (x == y)
					return@MatrixC Complex.ONE
				Complex.ZERO
			})
		}
	}
	
}

operator fun Complex.times(you: MatrixC) = MatrixC(you.width, you.height, { x, y -> you[x, y] * this })
operator fun Double.times(you: MatrixC) = MatrixC(you.width, you.height, { x, y -> you[x, y] * this })
