package math.combinatoric

import math.digits.FixedDigitBasedCounter
import java.util.*


// a,b,c -> a b c, abc, acb, bac, bca, cab, cba, ab c, ac b, ba c, bc a, ca b, cb a
fun <T> Set<T>.partitionIntoSublists(): Sequence<HashSet<ArrayList<T>>> {
	val partitions = this.createPartitions()
	return partitions.asSequence().flatMap { partition ->
		val perms = partition.map { set -> set.createPermutations() }.toCollection(arrayListOf<ArrayList<ArrayList<T>>>())
		val dbc = FixedDigitBasedCounter(perms.size, { perms[it].size })
		val solutions = ArrayList<HashSet<ArrayList<T>>>()
		while (true) {
			dbc.increment()
			if (dbc.done())
				break
			val solution = HashSet<ArrayList<T>>()
			for ((p, i) in dbc.digits.withIndex()) {
				solution.add(perms[p][i])
			}
			solutions.add(solution)
		}
		return@flatMap solutions.asSequence()
	}
}

//a,b,c -> a b c, ab c, ac b, bc a, abc
fun <T> Set<T>.createPartitions(): ArrayList<HashSet<HashSet<T>>> {
	val solutions = ArrayList<HashSet<HashSet<T>>>()
	solutions.add(HashSet())
	for (e in this) {
		val newSolutions = ArrayList<HashSet<HashSet<T>>>()
		for (solution in solutions) {
			for (set in solution) {
				val newSolution = HashSet<HashSet<T>>()
				solution.forEach {
					val hs = HashSet(it)
					if (it == set)
						hs.add(e)
					newSolution.add(hs)
				}
				newSolutions.add(newSolution)
			}
			solution.add(hashSetOf(e)) // element in new set
		}
		solutions.addAll(newSolutions)
	}
	return solutions
}