from functools import reduce
from typing import List, Tuple, Generator


def generate_divisors(prime_factorization: List[Tuple[int, int]]) -> Generator[int, None, None]:
	num_factors = len(prime_factorization)
	f = [0] * num_factors
	while True:
		yield reduce(lambda x, y: x * y,
		             [prime_factorization[x][0] ** f[x] for x in range(num_factors)], 1)
		i = 0
		while True:
			f[i] += 1
			if f[i] <= prime_factorization[i][1]:
				break
			f[i] = 0
			i += 1
			if i >= num_factors:
				return
