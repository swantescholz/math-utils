from functools import reduce
from typing import List


def chinese_remainder(prime_powers: List[int], remainders: List[int]) -> int:
	sum = 0
	prod = reduce(lambda a, b: a * b, prime_powers)
	for p, r in zip(prime_powers, remainders):
		d = prod // p
		sum += r * mul_inv(d, p) * d
		sum %= prod
	return sum


def mul_inv(a: int, b: int) -> int:
	b0 = b
	x0, x1 = 0, 1
	if b == 1: return 1
	while a > 1:
		q = a // b
		a, b = b, a % b
		x0, x1 = x1 - q * x0, x0
	if x1 < 0: x1 += b0
	return x1


if __name__ == "__main__":
	assert chinese_remainder([9, 8, 7], [4, 3, 2]) == 499
