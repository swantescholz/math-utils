package datastructures

import extensions.compareTo
import extensions.iterator
import math.linearalgebra.Coordi
import util.astIn
import java.util.*

// rows first bitset based bitmap
data class BitMap(val width: Int, val height: Int = width) : Comparable<BitMap>, Iterable<Coordi> {
	override fun hashCode(): Int {
		return data.hashCode()
	}
	
	override fun equals(other: Any?): Boolean {
		other as BitMap
		return data == other.data
	}
	
	private class MyIterator(val bitMap: BitMap) : Iterator<Coordi> {
		private val iterator = bitMap.data.iterator()
		override fun next(): Coordi {
			val index = iterator.next()
			return Coordi(index % bitMap.width, index / bitMap.width)
		}
		
		override fun hasNext(): Boolean = iterator.hasNext()
	}
	
	override fun iterator(): Iterator<Coordi> = MyIterator(this)
	
	override fun compareTo(other: BitMap): Int = data.compareTo(other.data)
	
	val data = BitSet(width * height)
	
	var cardinality: Long = 0
		private set
	
	operator fun get(x: Int, y: Int): Boolean {
		checkBounds(x, y)
		return data.get(y * width + x)
	}
	
	operator fun get(index: Int): Boolean {
		astIn(index, 0..width * height - 1)
		return data.get(index)
	}
	
	private fun checkBounds(x: Int, y: Int) {
		astIn(x, 0..width - 1)
		astIn(y, 0..height - 1)
	}
	
	operator fun set(x: Int, y: Int, value: Boolean) {
		checkBounds(x, y)
		if (value xor this[x, y]) {
			cardinality += if (value) 1 else -1
			data.set(y * width + x, value)
		}
	}
	
	operator fun set(index: Int, value: Boolean) {
		val (x, y) = Pair(index % width, index / width)
		this[x, y] = value
	}
	
	override fun toString(): String {
		return "${height}x$width:(${iterator().asSequence().
				map { "${it.x} ${it.y}" }.joinToString(separator = ", ")})"
	}
}