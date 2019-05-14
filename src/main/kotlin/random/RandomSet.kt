package random

import java.util.*

class RandomSet<E> : AbstractSet<E> {

	private val data = ArrayList<E>()
	private val indices = HashMap<E, Int>()

	constructor() {
	}

	constructor(items: Collection<E>) {
		for (item in items) {
			indices.put(item, data.size)
			data.add(item)
		}
	}

	override fun add(element: E): Boolean {
		if (indices.containsKey(element)) {
			return false
		}
		indices.put(element, data.size)
		data.add(element)
		return true
	}

	//Override element at position `id` with last element.
	fun removeAt(id: Int): E? {
		if (id >= data.size) {
			return null
		}
		val res = data[id]
		indices.remove(res)
		val last = data.removeAt(data.size - 1)
		if (id < data.size) {
			indices.put(last, id)
			data.set(id, last)
		}
		return res
	}

	override fun remove(element: E): Boolean {
		val index = indices[element] ?: return false
		removeAt(index)
		return true
	}

	operator fun get(i: Int): E {
		return data[i]
	}

	fun pollRandom(rnd: Random = RANDOM): E? {
		if (data.isEmpty()) {
			return null
		}
		val id = rnd.nextInt(data.size)
		return removeAt(id)
	}

	override val size: Int
		get() = data.size

	override fun iterator(): MutableIterator<E> {
		return data.iterator()
	}
}