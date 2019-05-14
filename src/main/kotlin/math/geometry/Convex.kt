package math.geometry

import extensions.sequences.prepend

// don't mutate index array!
// returns points in digit-reversed lexicographic order
fun seqIndicesForConvexShape(length: Int, tooBigFunction: (Array<Int>) -> Boolean)
		: Sequence<Array<Int>> {
	val coordinates = Array(length, { 0 })
	if (tooBigFunction(coordinates))
		return emptySequence()

	return generateSequence {
		var index = 0
		while (true) {
			if (index >= length) {
				break
			}
			coordinates[index]++
			if (!tooBigFunction(coordinates)) {
				return@generateSequence coordinates
			}
			coordinates[index] = 0
			index++
		}
		return@generateSequence null
	}.prepend(coordinates)
}