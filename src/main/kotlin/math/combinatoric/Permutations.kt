package math.combinatoric

import extensions.sequences.seq
import extensions.sequences.takeUntilNull
import extensions.toArrayList
import math.digits.FixedDigitBasedCounter
import math.digits.countDigits
import java.util.*


fun Long.isPermutationOf(you: Long): Boolean {
	val a = this.countDigits()
	val b = you.countDigits()
	for (i in 0..a.size - 1) {
		if (a[i] != b[i]) {
			return false
		}
	}
	return true
}

private fun <T> _create_perms(ss: LinkedList<T>): ArrayList<ArrayList<T>> {
	if (ss.size == 0)
		return arrayListOf(ArrayList<T>())
	if (ss.size == 1)
		return arrayListOf(arrayListOf(ss.first()))
	val results = ArrayList<ArrayList<T>>()
	val ssCopy = LinkedList(ss)
	val ssIterator = ssCopy.listIterator()
	val usedFirsts = HashSet<T>()
	while (ssIterator.hasNext()) {
		val first = ssIterator.next()
		if (first !in usedFirsts) {
			usedFirsts.add(first)
			ssIterator.remove()
			for (perm in _create_perms(ssCopy)) {
				perm.add(first)
				results.add(perm)
			}
			ssIterator.add(first)
		}
	}
	return results
}

fun <T> Iterable<T>.createPermutations(): ArrayList<ArrayList<T>> {
	val results = _create_perms(LinkedList(this.toList()))
	for (r in results) {
		r.reverse()
	}
	return results
}

fun <T> Iterable<T>.seqPermutations(): Sequence<ArrayList<T>> {
	val data = this.toArrayList()
	val size = data.size
	val dbc = FixedDigitBasedCounter(data.size, { x -> x + 1 })
	val bs = BitSet(data.size)
	val revDigits = dbc.digits.asReversed()
	return 1.seq().map {
		dbc.increment()
		if (dbc.done())
			return@map null
		bs.clear()
		val res = ArrayList<T>()
		for (offset in revDigits) {
			var unusedElementCounter = 0
			for (elementIndex in 0..size - 1) {
				if (bs.get(elementIndex) == false) {
					if (unusedElementCounter == offset) {
						bs.set(elementIndex)
						res.add(data[elementIndex])
						break
					} else {
						unusedElementCounter++
					}
				}
			}
		}
		res
	}.takeUntilNull()
}
