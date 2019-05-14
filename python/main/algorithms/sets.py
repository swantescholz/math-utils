import itertools
from typing import TypeVar, Set, Callable

T = TypeVar('T')


def inclusion_exclusion(base_sets_representatives: Set[T],
                        intersection_cardinality_function: Callable[[Set[T]], int]) -> int:
	""":returns cardinality of union of base sets, using inclusion exclusion principle"""
	res = 0
	sign = 1
	S = base_sets_representatives
	for size in range(1, len(S) + 1):
		for comb in itertools.combinations(S, size):
			res += sign * intersection_cardinality_function(set(comb))
		sign *= -1
	return res


if __name__ == "__main__":
	base = {2, 3, 5}
	n = 100
	import numpy as np
	
	
	def f(s):
		return n // np.product(list(s))
	
	
	assert 26 == n - inclusion_exclusion(base, f)
