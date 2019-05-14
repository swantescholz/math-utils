package datastructures

import java.util.*

class TwoKeyMap<A, B, V>() : Iterable<Triple<A, B, V>> {
	private class MyIterator<A, B, V>(val map: TwoKeyMap<A, B, V>) : Iterator<Triple<A, B, V>> {
		override fun hasNext(): Boolean = iterA.hasNext() || iterB?.hasNext() ?: false
		
		override fun next(): Triple<A, B, V> {
			if (iterB == null || !iterB!!.hasNext()) {
				val entry = iterA.next()
				currentA = entry.key
				iterB = entry.value.iterator()
			}
			val (b, value) = iterB!!.next()
			return Triple(currentA!!, b, value)
		}
		
		private var currentA: A? = null
		private val iterA = map.mapByA.iterator()
		private var iterB: Iterator<Map.Entry<B, V>>? = null
	}
	
	override fun iterator(): Iterator<Triple<A, B, V>> {
		return MyIterator(this)
	}
	
	private val mapByA = HashMap<A, HashMap<B, V>>()
	private val mapByB = HashMap<B, HashMap<A, V>>()
	
	val distinctAs: Int get() = mapByA.size
	val distinctBs: Int get() = mapByB.size
	var size: Int = 0
		private set
	
	operator fun contains(pair: Pair<A, B>): Boolean {
		if (pair.first !in mapByA)
			return false
		return pair.second in mapByA[pair.first]!!
	}
	
	fun contains(a: A, b: B): Boolean {
		if (a !in mapByA)
			return false
		return b in mapByA[a]!!
	}
	
	override fun toString(): String {
		val sb = StringBuilder()
		for ((a, hb) in mapByA) {
			sb.append("$a: $hb\n")
		}
		return sb.toString()
	}
	
	operator fun get(a: A, b: B): V = mapByA[a]!![b]!!
	operator fun set(a: A, b: B, value: V) {
		if (!contains(a, b))
			size++
		if (a !in mapByA)
			mapByA[a] = HashMap<B, V>()
		if (b !in mapByB)
			mapByB[b] = HashMap<A, V>()
		mapByA[a]!![b] = value
		mapByB[b]!![a] = value
	}
	
	fun remove(a: A, b: B) {
		if (contains(a, b))
			size--
		mapByA[a]?.remove(b)
		mapByB[b]?.remove(a)
		if (mapByA[a]?.size == 0)
			mapByA.remove(a)
		if (mapByB[b]?.size == 0)
			mapByB.remove(b)
	}
	
	fun getByA(a: A): HashMap<B, V> = mapByA[a] ?: HashMap()
	fun getByB(b: B): HashMap<A, V> = mapByB[b] ?: HashMap()
	
}