package math.linearalgebra

import datastructures.DefaultHashMap
import extensions.sequences.seq
import util.astEqual
import util.astTrue
import util.range
import java.util.*

class SparseMatrix(val width: Int, val height: Int = width) {
	private val rows = ArrayList<HashMap<Int, Double>>()

	init {
		for (y in 0..height - 1) {
			val row = DefaultHashMap<Int, Double>({ 0.0 }, false)
			rows.add(row)
		}
	}

	operator fun get(x: Int, y: Int) = rows[y][x]!!

	operator fun get(xyPair: Pair<Int, Int>) = this[xyPair.first, xyPair.second]

	operator fun set(x: Int, y: Int, value: Double) {
		if (value == 0.0)
			rows[y].remove(x)
		else
			rows[y][x] = value
	}

	operator fun set(xyPair: Pair<Int, Int>, value: Double) {
		this[xyPair.first, xyPair.second] = value
	}


	operator fun unaryMinus(): SparseMatrix {
		val res = SparseMatrix(width, height)
		for (y in range(height)) {
			for (entry in rows[y]) {
				res[entry.key, y] = -entry.value
			}
		}
		return res
	}

	operator fun plus(you: SparseMatrix): SparseMatrix {
		astEqual(this.width, you.width)
		astEqual(this.height, you.height)
		val res = SparseMatrix(width, height)
		for (y in range(height)) {
			for (entry in rows[y]) {
				res[entry.key, y] = entry.value
			}
			for (entry in you.rows[y]) {
				res[entry.key, y] += entry.value
			}
		}
		return res
	}

	operator fun minus(you: SparseMatrix): SparseMatrix {
		astEqual(this.width, you.width)
		astEqual(this.height, you.height)
		val res = SparseMatrix(width, height)
		for (y in range(height)) {
			for (entry in rows[y]) {
				res[entry.key, y] = entry.value
			}
			for (entry in you.rows[y]) {
				res[entry.key, y] -= entry.value
			}
		}
		return res
	}

	operator fun times(you: Double): SparseMatrix {
		val res = SparseMatrix(width, height)
		for (y in range(height)) {
			for (entry in rows[y]) {
				res[entry.key, y] = entry.value * you
			}
		}
		return res
	}

	operator fun mod(you: Double): SparseMatrix {
		val res = SparseMatrix(width, height)
		for (y in range(height)) {
			for (entry in rows[y]) {
				res[entry.key, y] = entry.value % you
			}
		}
		return res
	}

	operator fun div(you: Double) = this * (1 / you)

	//slow!!
	operator fun times(you: SparseMatrix): SparseMatrix {
		astTrue(this.width == you.height)
		val res = SparseMatrix(you.width, this.height)
		for (x in range(you.width)) {
			for (y in range(this.height)) {
				res[x, y] = 0.seq(width - 1).map {
					this[it, y] * you[x, it]
				}.sum()
			}
		}
		return res
	}

	// todo faster
	operator fun times(you: VectorD): VectorD {
		astTrue(this.width == you.size)
		return VectorD(this.height, { y ->
			var sum = 0.0
			for (entry in rows[y]) {
				sum += you[entry.key] * entry.value
			}
			sum
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

	fun sum(): Double {
		var sum = 0.0
		for (y in range(height)) {
			sum += rows[y].values.sum()
		}
		return sum
	}
}

operator fun Double.times(you: SparseMatrix) = you * this