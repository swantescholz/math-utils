package datastructures

import java.util.*

class Multiset<T>() : Iterable<T> {


	private val map = HashMap<T, Int>()
	var total: Int = 0
		private set

	constructor(you: Multiset<T>) : this() {
		map.putAll(you.map)
		total = you.total
	}

	constructor(data: Iterable<T>) : this() {
		for (element in data)
			add(element)
	}

	override fun hashCode() = map.hashCode()
	override fun equals(other: Any?): Boolean {
		if (other == null)
			return false
		val you = other as Multiset<*>
		return map.equals(you.map)
	}

	operator fun get(element: T): Int = map[element] ?: 0

	fun countDistinct() = map.size

	fun add(e: T) {
		map.put(e, (map.get(e) ?: 0) + 1)
		total++
	}

	fun removeAll(e: T) {
		map.remove(e)
	}

	fun remove(e: T) {
		if (e in map) {
			val count = map.get(e)
			if (count == 1)
				map.remove(e)
			else
				map.put(e, map.get(e)!! - 1)
			total--
		}
	}

	override fun iterator(): Iterator<T> = map.keys.iterator()
	override fun toString(): String = map.toString()
}