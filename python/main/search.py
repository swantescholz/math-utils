import typing


def ternary_search_max(a, b, f, epsilon=1e-9):
	""":returns x such that f(x) is (close to) the maximum in the interval [a,b],
	f has to be increasing, then decreasing, with only that one extremum"""
	while True:
		if abs(b - a) < epsilon:
			return (a + b) / 2
		m1 = a + (b - a) / 3
		m2 = b - (b - a) / 3
		if f(m1) < f(m2):
			a = m1
		else:
			b = m2


def binary_search_floats(a: float, b: float, is_too_large: typing.Callable[[float], bool],
                         epsilon=1e-9) -> float:
	if is_too_large(a):
		return a
	if not is_too_large(b):
		return b
	while b - a > epsilon:
		m = (a + b) / 2
		if is_too_large(m):
			b = m
		else:
			a = m
	return a


def exponential_binary_search_floats(lower_bound: float,
                                     is_too_large: typing.Callable[[float], bool]) -> float:
	if is_too_large(lower_bound):
		return lower_bound
	d = 1
	b = lower_bound + d
	while not is_too_large(b):
		d *= 2
		lower_bound, b = b, b + d
	return binary_search_floats(lower_bound, b, is_too_large)


def binary_search_ints(lowest_possible_index_inclusive: int, highest_possible_index_inclusive: int,
                       index_is_too_large_function: typing.Callable[[int], bool]) -> int:
	""":returns largest index in range that is not too large,
	or lowest possible index - 1 if none found, (and high, when all are not too large)"""
	a = lowest_possible_index_inclusive
	b = highest_possible_index_inclusive
	if index_is_too_large_function(a):
		return a - 1
	if not index_is_too_large_function(b):
		return b
	while b > a + 1:
		m = (a + b) // 2
		if index_is_too_large_function(m):
			b = m
		else:
			a = m
	return a


def exponential_binary_search_ints(lowest_possible_index_inclusive: int,
                                   index_is_too_large_function: typing.Callable[[int], bool]) -> int:
	""":returns largest index in range that is not too large,
	or lowest possible index - 1 if none > lower-bound found"""
	a = lowest_possible_index_inclusive
	if index_is_too_large_function(a):
		return a - 1
	d = 1
	b = a + d
	while not index_is_too_large_function(b):
		d *= 2
		a, b = b, b + d
	return binary_search_ints(a, b, index_is_too_large_function)


if __name__ == "__main__":
	print(binary_search_ints(2, 22222, lambda i: i > 111))
	print(exponential_binary_search_ints(22, lambda i: i > 456))
