package math.digits

import java.util.*

class ShortenedBinaryExpansion() : ArrayList<Int>() {

	constructor(values: Collection<Int>) : this() {
		addAll(values)
	}

	companion object {
		fun fromLong(value: Long): ShortenedBinaryExpansion {
			val res = ShortenedBinaryExpansion()
			var l = value
			while (true) {
				var ones = 0
				while (l % 2 == 1L) {
					l /= 2
					ones++
				}
				res.add(ones)
				if (l == 0L)
					break
				var zeros = 0
				while (l % 2 == 0L) {
					l /= 2
					zeros++
				}
				res.add(zeros)
			}
			return res
		}
	}
}
