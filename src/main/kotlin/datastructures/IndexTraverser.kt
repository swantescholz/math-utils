package datastructures

class IndexTraverser(val length: Int, private val stillOkayFunction: (Array<Int>) -> Boolean) {
	val indices = Array(length, { 0 })
	private var doneRounds = 0

	override fun toString(): String {
		return indices.toString()
	}

	fun increment() {
		var index = 0
		while (true) {
			if (index >= length) {
				doneRounds++
				break
			}
			indices[index]++
			if (stillOkayFunction(indices)) {
				break
			}
			indices[index] = 0
			index++
		}
	}

	fun done(): Boolean {
		return doneRounds > 0
	}
}