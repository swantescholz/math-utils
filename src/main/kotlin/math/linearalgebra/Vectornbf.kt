package math.linearalgebra

import extensions.*
import extensions.sequences.seq
import extensions.sequences.sum
import org.apache.commons.math3.fraction.BigFraction
import util.astTrue


class Vectornbf(val size: Int, fillFunction: (Int) -> BigFraction = { BigFraction.ZERO }) {
	
	val data = Array(size, fillFunction)
	
	operator fun get(index: Int) = data[index]
	operator fun set(index: Int, value: BigFraction) {
		data[index] = value
	}
	
	operator fun unaryMinus() = Vectornbf(size, { -this[it] })
	operator fun plus(you: Vectornbf) = Vectornbf(size, { this[it] + you[it] })
	operator fun minus(you: Vectornbf) = Vectornbf(size, { this[it] - you[it] })
	operator fun times(you: BigFraction) = Vectornbf(size, { this[it] * you })
	operator fun div(you: BigFraction) = Vectornbf(size, { this[it] / you })
	
	override fun toString(): String {
		var text = ""
		for (x in 0..size - 1) {
			text += this[x].toString() + " "
		}
		return text.removeSuffix(" ")
	}

	
	private fun asSequence(): Sequence<BigFraction> {
		return data.asSequence()
	}
	

	fun sum() = asSequence().sum()
}

operator fun BigFraction.times(you: Vectornbf) = Vectornbf(you.size, { you[it] * this })
operator fun SparseMatrix.times(you: Vectornbf): Vectornbf {
	astTrue(this.width == you.size)
	return Vectornbf(this.height, { y ->
		0.seq(width - 1).map { x ->
			this[x, y].toLong() * you[x]
		}.sum()
	})
}