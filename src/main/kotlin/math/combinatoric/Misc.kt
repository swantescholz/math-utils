package math.combinatoric


// choose r out of n
infix fun Long.nCr(r: Long): Long {
	if (r < 0 || r > this)
		return 0
	if (r == 0L || r == this)
		return 1
	var res = 1L
	var n = this
	var rvar = r
	if (rvar > n - rvar)
		rvar = n - rvar
	for (ir in 2..rvar) {
		while (res % ir != 0L) {
			res *= n
			n--
		}
		res /= ir
	}
	while (n > this - rvar) {
		res *= n
		n--
	}
	return res
}

fun createFactorials(count: Int): Array<Long> {
	val a = Array<Long>(count, { 1 })
	for (i in 1..count - 1) {
		a[i] = i * a[i - 1]
	}
	return a
}

private tailrec fun Long._fac(tmp: Long): Long {
	if (this < 0)
		return 0
	if (this <= 1)
		return tmp
	return (this - 1)._fac(this * tmp)
}

fun Long.fac(): Long {
	return this._fac(1L)
}