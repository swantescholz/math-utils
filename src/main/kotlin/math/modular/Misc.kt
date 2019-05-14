package math.modular

fun Int.modPow(exponent: Long, modulus: Int): Int {
	var res = 1L
	var a  = this.toLong()
	var b = exponent
	while (b > 0) {
		if (b % 2 != 0L) {
			res = (res * a) % modulus
		}
		a = (a * a) % modulus
		b /= 2
	}
	return res.toInt()
}