package math.linearalgebra

import extensions.*
import extensions.sequences.seq
import org.apache.commons.math3.complex.Complex
import util.astTrue

class VectorC(val size: Int, fillFunction: (Int) -> Complex = { Complex.ZERO }) {
	
	constructor(c: Collection<Complex>) : this(c.size) {
		val iter = c.iterator()
		for (i in 0..size - 1) {
			this[i] = iter.next()
		}
	}
	
	val data = Array(size, fillFunction)
	
	operator fun get(index: Int) = data[index]
	operator fun set(index: Int, value: Complex) {
		data[index] = value
	}
	
	operator fun unaryMinus() = VectorC(size, { -this[it] })
	operator fun plus(you: VectorC) = VectorC(size, { this[it] + you[it] })
	operator fun minus(you: VectorC) = VectorC(size, { this[it] - you[it] })
	operator fun times(you: Double) = VectorC(size, { this[it] * you })
	operator fun div(you: Double) = VectorC(size, { this[it] / you })
	operator fun times(you: MatrixD): VectorC {
		astTrue(this.size == you.height)
		return VectorC(you.width, { x ->
			0.seq(you.height - 1).map { y ->
				this[y] * you[x, y]
			}.reduce { a, b -> a + b }
		})
	}
	
	override fun toString(): String {
		var text = ""
		for (x in 0..size - 1) {
			text += this[x].toString() + " "
		}
		return text.removeSuffix(" ")
	}
	
}

operator fun Double.times(you: VectorC) = VectorC(you.size, { you[it] * this })
operator fun Complex.times(you: VectorC) = VectorC(you.size, { you[it] * this })