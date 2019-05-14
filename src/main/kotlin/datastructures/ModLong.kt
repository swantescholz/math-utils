package datastructures

import extensions.modPow
import util.astGreater
import util.astGreaterEqual

class ModLong(val m: Long, initialValue: Long = 0L) {
	val value: Long = initialValue % m

	init {
		astGreaterEqual(value, 0L)
		astGreater(m, 0L)
	}

	override fun toString(): String {
		return "$value (mod $m)"
	}

	fun pow(exponent: Long): ModLong {
		return ModLong(m, value.modPow(exponent, m))
	}

	infix operator fun plus(you: ModLong) = this + you.value
	infix operator fun minus(you: ModLong) = this - you.value
	infix operator fun times(you: ModLong) = this * you.value
	infix operator fun div(you: ModLong) = this / you.value
	infix operator fun mod(you: ModLong) = this % you.value

	infix operator fun plus(you: Int) = this + you.toLong()
	infix operator fun minus(you: Int) = this - you.toLong()
	infix operator fun times(you: Int) = this * you.toLong()
	infix operator fun div(you: Int) = this / you.toLong()
	infix operator fun mod(you: Int) = this % you.toLong()

	infix operator fun plus(you: Long) = ModLong(m, value + (you % m))
	infix operator fun minus(you: Long) = ModLong(m, m + value - (you % m))
	infix operator fun times(you: Long) = ModLong(m, value * (you % m))
	infix operator fun div(you: Long) = ModLong(m, value / you)
	infix operator fun mod(you: Long) = ModLong(m, value % you)

	operator fun inc(): ModLong {
		return ModLong(m, value + 1)
	}

	operator fun dec(): ModLong {
		return ModLong(m, m + value - 1)
	}
}


