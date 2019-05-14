import operator
import operator as op
from functools import reduce
from typing import List


def int_to_bits(i: int) -> List[bool]:
	return [digit == '1' for digit in bin(i)[2:]]


def ncr(n: int, r: int) -> int:
	r = min(r, n - r)
	if r < 0: return 0
	if r == 0: return 1
	numer = reduce(op.mul, range(n, n - r, -1))
	denom = reduce(op.mul, range(1, r + 1))
	return numer // denom


def ncr_multinomial(n: int, rs: List[int]) -> int:
	if any(r < 0 for r in rs):
		return 0
	if sum(rs) != n:
		return 0
	if n == 0:
		return 1
	res = reduce(op.mul, range(1, n + 1))
	for r in rs:
		res //= reduce(op.mul, range(1, r + 1))
	return res


def product(iterable) -> int:
	return reduce(operator.mul, iterable, 1)


def even(n: int) -> bool:
	return n % 2 == 0


def odd(n: int) -> bool:
	return n % 2 == 1


if __name__ == "__main__":
	assert ncr_multinomial(10, [6, 2, 2]) == 10 * 9 * 8 * 7 // 2 // 2
