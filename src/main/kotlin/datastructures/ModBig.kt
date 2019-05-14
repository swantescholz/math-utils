package datastructures

import math.BIG0
import util.extensions.big
import java.math.BigInteger

class ModBig(val modulus: BigInteger, initialValue: BigInteger = BIG0) {

	var value: BigInteger = initialValue % modulus
		set(value) {
			this.value = value % modulus
		}


	operator fun plus(you: BigInteger) = ModBig(modulus, value + (you % modulus))
	operator fun minus(you: BigInteger) = ModBig(modulus, value - (you % modulus) + modulus)
	operator fun times(you: BigInteger) = ModBig(modulus, value * (you % modulus))
	operator fun mod(you: BigInteger) = ModBig(modulus, value % (you % modulus))
	fun pow(you: BigInteger) = ModBig(modulus, value.modPow(you, modulus))
	operator fun plus(you: Long) = this + you.big
	operator fun minus(you: Long) = this - you.big
	operator fun times(you: Long) = this * you.big
	operator fun mod(you: Long) = this % you.big
	fun pow(you: Long) = this.pow(you.big)
	operator fun plus(you: Int) = this + you.big
	operator fun minus(you: Int) = this - you.big
	operator fun times(you: Int) = this * you.big
	operator fun mod(you: Int) = this % you.big
	fun pow(you: Int) = this.pow(you.big)
	operator fun plus(you: ModBig) = this + you.value
	operator fun minus(you: ModBig) = this - you.value
	operator fun times(you: ModBig) = this * you.value
	operator fun mod(you: ModBig) = this % you.value
	fun pow(you: ModBig) = this.pow(you.value)


	override fun toString(): String = "$value % $modulus"
}