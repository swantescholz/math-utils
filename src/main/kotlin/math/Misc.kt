package math

import extensions.signum

fun convertWolfram(str: String): String {
	var result = str.replace(" ", "*")
	result = result.replace("^2", ".pow(2)")
	result = result.replace("^3", ".pow(3)")
	result = result.replace("^4", ".pow(4)")
	result = result.replace("^5", ".pow(5)")
	result = result.replace("^6", ".pow(6)")
	return result
}

fun productOverflows(a: Long, b: Long): Boolean {
	val maximum = if (a.signum() == b.signum()) Long.MAX_VALUE else Long.MIN_VALUE
	if (a == 0L || b == 0L)
		return false
	if (b > 0)
		return b > maximum / a
	return b < maximum / a
}

fun sqrt(x: Double) = Math.sqrt(x)