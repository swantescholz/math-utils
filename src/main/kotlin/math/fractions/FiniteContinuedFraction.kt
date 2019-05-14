package math.fractions

import extensions.minus
import extensions.plus
import org.apache.commons.math3.fraction.Fraction
import util.astGreaterEqual
import java.util.*


class FiniteContinuedFraction() : ArrayList<Int>() {

	constructor(values: Collection<Int>) : this() {
		addAll(values)
	}

	fun toFraction(): Fraction {
		if (this.isEmpty())
			return Fraction.ZERO
		var res = Fraction(this.last())
		for (i in size - 2 downTo 0) {
			res = Fraction(this[i]) + res.reciprocal()
		}
		return res
	}

	companion object {
		fun fromFraction(fraction: Fraction): FiniteContinuedFraction {
			astGreaterEqual(fraction, Fraction.ZERO)
			val res = FiniteContinuedFraction()
			var f = fraction
			while (true) {
				val i = f.toInt()
				res.add(i)
				f -= i
				if (f == Fraction.ZERO)
					break
				f = f.reciprocal()
			}
			return res
		}
	}
}