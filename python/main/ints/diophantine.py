import ast
from typing import Tuple, List

from ints import series, primes, divisors


def compute_linear_diophantine_coefficient_candidates(c: int, amax: int,
                                                      bmax: int) -> List[Tuple[int, int]]:
	"""
	:returns list of non-negative integers (a,b) <= (amax, bmax),
	with ax+by=c>0 having integer solutions for x,y
	(i.e. gdc(a,b) divides c)
	"""
	assert c > 0, f"c ({c}) should be positive"
	candidates = set()
	pf = primes.prime_factorize(c)
	coprime_pairs = set()
	for a, b in series.farey_sequence(max(amax, bmax)):
		if a <= amax and b <= bmax:
			coprime_pairs.add((a, b))
		if b <= amax and a <= bmax:
			coprime_pairs.add((b, a))
	xys = sorted(coprime_pairs)
	yxs = sorted(coprime_pairs, key=lambda it: it[1])
	for gdc in divisors.generate_divisors(pf):
		for x, y in xys:
			a, b = gdc * x, gdc * y
			if a > amax:
				break
			if b > bmax:
				continue
			candidates.add((a, b))
		for x, y in yxs:
			a, b = gdc * x, gdc * y
			if b > bmax:
				break
			if a > amax:
				continue
			candidates.add((a, b))
	return sorted(list(candidates))


if __name__ == "__main__":
	primes.make_primes_up_to(1000)
	n, amax, bmax = 100, 15, 15
	import math
	
	count = 0
	for a in range(amax + 1):
		for b in range(bmax + 1):
			if a == 0 and b == 0:
				continue
			if n % math.gcd(a, b) == 0:
				count += 1
	l = compute_linear_diophantine_coefficient_candidates(n, amax, bmax)
	for a, b in l:
		assert n % math.gcd(a, b) == 0
		print(a, b)
	ast.ast_equals(len(l), count)
