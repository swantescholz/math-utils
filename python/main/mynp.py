from typing import Tuple, Optional

import numpy as np


def gcd(a, b) -> int:
	# a, b = np.broadcast_arrays(a, b)
	assert a.shape == b.shape
	shape = a.shape
	a = a.flatten()
	b = b.flatten()
	pos = np.nonzero(b)[0]
	while len(pos) > 0:
		b2 = b[pos]
		a[pos], b[pos] = b2, a[pos] % b2
		pos = pos[b[pos] != 0]
	return a.reshape(shape)


def repeat_row(row_as_np_array, number_of_rows):
	return np.tile(row_as_np_array, (number_of_rows, 1))


def repeat_column(column_as_np_array, number_of_columns):
	return np.tile(np.array([column_as_np_array]).transpose(), (1, number_of_columns))


def tile_in_dimension(a: np.array, desired_shape: Tuple[int, ...], dimension: int):
	""":returns a hyper cuboid numpy array of desired shape with the given 1d array tiled in given direction"""
	assert len(a.shape) == 1, "should be one-dimensional!"
	assert a.shape[0] == desired_shape[dimension], "wrong array size"
	return np.tile(a.reshape(
		[1] * (dimension - 1) + [desired_shape[dimension]] + [1] * (len(desired_shape) - dimension - 1)),
		[desired_shape[it] for it in range(dimension)] + [1] +
		[desired_shape[it] for it in range(dimension + 1, len(desired_shape))])


def matrix_modpow(matrix: np.ndarray, exponent: int, mod: Optional[int] = None) -> np.ndarray:
	if mod is None:
		return np.linalg.matrix_power(matrix, exponent)
	x = np.zeros(shape=matrix.shape, dtype=np.int64)
	for i in range(matrix.shape[0]):
		x[i, i] = 1
	bits = "{0:b}".format(exponent)
	for i, bit in enumerate(bits):
		if bit == '1':
			x = (((x.dot(x) % mod) @ (matrix)) % mod)
		elif bit == '0':
			x = ((x.dot(x)) % mod)
	return x % mod


if __name__ == "__main__":
	m = np.array([1, 1, 1, 0]).reshape((2, 2))
	x = matrix_modpow(m, 10, 50)
	assert np.all(x == np.array([39, 5, 5, 34]).reshape((2, 2)))
