import numpy as np

import linalg


def geometric_series_finite_sum(z, n, mod=None):
	"""returns fast sum(z**k for k in range(n+1)) (% mod, if provided)"""
	if n < 0:
		return 0
	if mod is None:
		return (1 - z ** (n + 1)) // (1 - z)
	M = np.array([z, 0, 1, 1]).reshape((2, 2))
	return linalg.matrix_modpow(M, n + 1, mod)[1, 0]


def geometric_series_with_linear_coefficient_finite_sum(z, n, mod=None):
	"""returns fast sum(k*z**k for k in range(n+1)) (% mod, if provided)"""
	if n < 0:
		return 0
	if mod is None:
		return z * (1 - (n + 1) * z ** n + n * z ** (n + 1)) // (1 - z) ** 2
	M = np.array([z, z, z, 0, z, z, 0, 0, 1]).reshape((3, 3))
	return linalg.matrix_modpow(M, n, mod)[0, 2]


def farey_sequence(n):
	"""Print the nth Farey sequence (denominator <= n)"""
	a, b, c, d = 0, 1, 1, n
	yield (a, b)
	while c <= n:
		k = (n + b) // d
		a, b, c, d = c, d, (k * c - a), (k * d - b)
		yield (a, b)
