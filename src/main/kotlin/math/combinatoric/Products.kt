package math.combinatoric

import datastructures.treeSetOf
import extensions.toArrayList
import math.LMAX
import java.util.*

// [a,b,c] -> a,aa,aaa,ab,aab,abb, etc. sorted
fun ArrayList<Long>.computeProductsOfPowers(maxProduct: Long = LMAX): ArrayList<Long> {
	val ts = treeSetOf(1L)
	for (n in this) {
		if (n > maxProduct)
			break
		val newElements = ArrayList<Long>()
		var pow = n
		while (pow <= maxProduct) {
			for (oldProduct in ts) {
				val newProduct = oldProduct * pow
				if (newProduct <= maxProduct) {
					newElements.add(newProduct)
				} else {
					break
				}
			}
			pow *= n
		}
		ts.addAll(newElements)
	}
	return ts.toArrayList()
}