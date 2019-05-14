package math.sequences

import extensions.sequences.seq
import java.util.*


fun genChainLoopLengths(upperBound: Long, nextFunction: (Long) -> Long): Sequence<Pair<Long, Int?>> {
	val checkedNumbers = HashSet<Long>()
	val chainLengths = HashMap<Long, Int>()
	return 0L.seq(upperBound).map {
		if (it in chainLengths)
			return@map Pair(it, chainLengths.get(it))
		var n = it
		var chainElements = LinkedHashSet<Long>()
		while (n !in chainElements) {
			if (n in checkedNumbers || n > upperBound) {
				checkedNumbers.addAll(chainElements)
				return@map Pair(it, null)
			}
			chainElements.add(n)
			n = nextFunction(n)
		}
		val iter = chainElements.iterator()
		var chainLength = chainElements.size
		while (iter.hasNext()) {
			val x = iter.next()
			if (x == n) {
				chainLengths.put(x, chainLength)
				break
			}
			checkedNumbers.add(x)
			chainLength--
		}
		while (iter.hasNext()) {
			chainLengths.put(iter.next(), chainLength)
		}
		return@map Pair(it, chainLength)
	}
}