from ints import ncr


def mod_pow(a: int, b: int, m: int) -> int:
	"""computes a**b % m fast"""
	res = 1
	while b > 0:
		if b % 2 != 0:
			res = (res * a) % m
		a = (a * a) % m
		b //= 2
	return res


def mod_inverse_of_prime(a: int, p: int) -> int:
	""":returns x, such that a*x % p == 1, assuming that p is prime"""
	return mod_pow(a, p - 2, p)


def ncr_mod_prime(n: int, r: int, p: int) -> int:
	""":returns ncr(n,r) % p, assuming p is prime, using lucas theorem.
	its fast if p is much smaller than n"""
	product = 1
	while n > 0 or r > 0:
		a, b = n % p, r % p
		n //= p
		r //= p
		product *= ncr(a, b) % p
		product %= p
	return product


if __name__ == "__main__":
	assert ncr(99, 44) % 37 == ncr_mod_prime(99, 44, 37)
