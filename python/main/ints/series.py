from typing import Generator, Tuple

import numpy as np

import linalg


def geometric_series_finite_sum(z: int, n: int, mod: int = None) -> int:
	"""returns fast sum(z**k for k in range(n+1)) (% mod, if provided)"""
	if n < 0:
		return 0
	if mod is None:
		return (1 - z ** (n + 1)) // (1 - z)
	M = np.array([z, 0, 1, 1]).reshape((2, 2))
	return linalg.matrix_modpow(M, n + 1, mod)[1, 0]


def geometric_series_with_linear_coefficient_finite_sum(z: int, n: int, mod=None) -> int:
	"""returns fast sum(k*z**k for k in range(n+1)) (% mod, if provided)"""
	if n < 0:
		return 0
	if mod is None:
		return z * (1 - (n + 1) * z ** n + n * z ** (n + 1)) // (1 - z) ** 2
	M = np.array([z, z, z, 0, z, z, 0, 0, 1]).reshape((3, 3))
	return linalg.matrix_modpow(M, n, mod)[0, 2]


def farey_sequence(n) -> Generator[Tuple[int, int], None, None]:
	"""yield the nth Farey sequence (denominator <= n) as generator of pairs (int,int)"""
	a, b, c, d = 0, 1, 1, n
	yield (a, b)
	while c <= n:
		k = (n + b) // d
		a, b, c, d = c, d, (k * c - a), (k * d - b)
		yield (a, b)


def longest_nondecreasing_subsequence(sequence):
	best_lengths = [1] * len(sequence)
	best_parents = [-1] * len(sequence)
	for a in range(1, len(sequence)):
		best_length = 0
		best_parent = -1
		for b in range(a):
			if sequence[b] <= sequence[a]:
				if best_length < best_lengths[b]:
					best_length = best_lengths[b]
					best_parent = b
		best_lengths[a] = best_length + 1
		best_parents[a] = best_parent
	i = max(range(len(sequence)), key=lambda it: best_lengths[it])
	res = [sequence[i]]
	while best_parents[i] != -1:
		i = best_parents[i]
		res.append(sequence[i])
	return res[::-1]
