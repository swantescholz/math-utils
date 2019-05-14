package datastructures

import extensions.sequences.seq


//indexed (x,y)
class TriangleArray<T> protected constructor(val width: Int, arrayFactory: (Int) -> Array<T>) {

	protected val data: Array<Array<T>> = Array(width, { x -> arrayFactory(x) })

	companion object {
		inline fun <reified T : Cloneable> createEmpty(width: Int): TriangleArray<T?>
				= TriangleArray(width, { x -> arrayOfNulls<T>(x + 1) })

		inline fun <reified T> create(width: Int, crossinline initFunction: (Int, Int) -> T): TriangleArray<T> {
			return TriangleArray(width, { x -> Array(x + 1, { y -> initFunction(x, y) }) })
		}

		inline fun <reified T> clone(you: TriangleArray<T>): TriangleArray<T> {
			return TriangleArray(you.width, { x -> Array(x + 1, { y -> you[x, y] }) })
		}


	}

	operator fun get(x: Int, y: Int): T {
		return data[x][y]
	}

	operator fun get(xyPair: Pair<Int, Int>): T {
		return data[xyPair.first][xyPair.second]
	}

	operator fun set(x: Int, y: Int, value: T) {
		data[x][y] = value
	}

	operator fun set(xyPair: Pair<Int, Int>, value: T) {
		data[xyPair.first][xyPair.second] = value
	}

	// returns [0,0], [1,0], [1,1], [2,0], ...
	fun asSequence(): Sequence<Triple<Int, Int, T>> {
		return 0.seq(width - 1).map { x ->
			0.seq(x).map { y ->
				Triple(x, y, this[x, y])
			}
		}.flatten()
	}

	override fun toString(): String {
		var text = ""
		for (x in 0..width - 1) {
			var row = ""
			for (y in 0..x) {
				row += this[x, y].toString() + " "
			}
			text += row.removeSuffix(" ") + "\n"
		}
		return text.removeSuffix("\n")
	}

}