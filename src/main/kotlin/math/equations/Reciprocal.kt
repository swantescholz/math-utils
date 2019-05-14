package math.equations

import extensions.Tuple3l

// gives all solutions for 1/a + 1/b = 1/c as sequence of triples (a,b,c)
fun solveAInvPlusBInvEqualsCInv(): Sequence<Tuple3l> {
	var k = 1L
	var m = 1L
	var n = 0L
	return generateSequence {
		if (n == m) {
			if (m == k) {
				k++
				m = 0L
			}
			m++
			n = 0L
		}
		n++
		Tuple3l(k * m * (m + n), k * n * (m + n), k * m * n)
	}
}