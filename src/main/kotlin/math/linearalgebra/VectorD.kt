package math.linearalgebra

import extensions.round
import extensions.sequences.seq
import math.NANO
import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.RealVector
import util.astTrue

class VectorD(val size: Int, fillFunction: (Int) -> Double = { 0.0 }) {
	
	constructor(vararg cooridinates: Double) : this(cooridinates.asList())
	
	constructor(c: Collection<Double>) : this(c.size) {
		val iter = c.iterator()
		for (i in 0..size - 1) {
			this[i] = iter.next()
		}
	}
	
	constructor(v: RealVector) : this(v.dimension) {
		for (x in 0..size - 1) {
			this[x] = v.getEntry(x)
		}
	}
	
	val data = Array(size, fillFunction)
	
	operator fun component1(): Double = data[0]
	operator fun component2(): Double = data[1]
	operator fun component3(): Double = data[2]
	operator fun component4(): Double = data[3]
	operator fun component5(): Double = data[4]
	operator fun component6(): Double = data[5]
	operator fun component7(): Double = data[6]
	operator fun component8(): Double = data[7]
	
	operator fun get(index: Int) = data[index]
	operator fun set(index: Int, value: Double) {
		data[index] = value
	}
	
	operator fun unaryMinus() = VectorD(size, { -this[it] })
	operator fun plus(you: VectorD) = VectorD(size, { this[it] + you[it] })
	operator fun minus(you: VectorD) = VectorD(size, { this[it] - you[it] })
	operator fun times(you: Double) = VectorD(size, { this[it] * you })
	operator fun mod(you: Double) = VectorD(size, { this[it] % you })
	operator fun div(you: Double) = VectorD(size, { this[it] / you })
	
	// returns dot product
	operator fun times(you: VectorD): Double {
		astTrue(this.size == you.size)
		return data.asSequence().zip(you.data.asSequence()).map {it.first * it.second}.sum()
	}
	
	operator fun times(you: MatrixD): VectorD {
		astTrue(this.size == you.height)
		return VectorD(you.width, { x ->
			0.seq(you.height - 1).map { y ->
				this[y] * you[x, y]
			}.sum()
		})
	}
	
	private fun doubleToString(d: Double): String {
		if (d == d.round().toDouble())
			return "${d.round()}"
		return "$d"
	}
	
	override fun toString(): String {
		return data.map { doubleToString(it) }.joinToString(" ")
	}
	
	fun toPolynomialString(xChar: String = "x"): String {
		
		return asSequence().withIndex().filter { it.value != 0.0 }
				.map {
					when (it.index) {
						0 -> doubleToString(it.value)
						1 -> "${doubleToString(it.value)}*$xChar"
						else -> "${doubleToString(it.value)}*$xChar^${it.index}"
					}
				}
				.map {
					if (it[0] != '-')
						return@map "+$it"
					return@map it
				}
				.toCollection(arrayListOf<String>()).reversed().joinToString("").removePrefix("+")
	}
	
	private fun asSequence(): Sequence<Double> {
		return data.asSequence()
	}
	
	fun evaluatePolynomial(x: Double): Double {
		return this.asSequence().withIndex()
				.map { it.value * Math.pow(x, it.index.toDouble()) }.sum()
	}
	
	fun sum(): Double = data.asSequence().sum()
	fun length(): Double = Math.sqrt(data.asSequence().map{it*it}.sum())
	fun length2(): Double = data.asSequence().map{it*it}.sum()
	
	fun toArrayRealVector(): ArrayRealVector {
		val res = ArrayRealVector(size)
		for (x in 0..size - 1)
			res.setEntry(x, this[x])
		return res
	}
}

operator fun Double.times(you: VectorD) = VectorD(you.size, { you[it] * this })