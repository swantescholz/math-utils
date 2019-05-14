package datastructures

import java.util.*

class ArrayList2<T> : ArrayList<ArrayList<T>>() {
}


data class CoordI2(val x: Int, val y: Int) : Comparable<CoordI2> {
	override fun compareTo(other: CoordI2): Int {
		if (x < other.x)
			return -1
		if (x > other.x)
			return 1
		return y.compareTo(other.y)
	}
}

data class CoordI3(val x: Int, val y: Int, val z: Int)
