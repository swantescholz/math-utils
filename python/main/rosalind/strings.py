import math
from typing import List

import util
from ints.misc import ncr
from util import print_regularly


def levenshtein_distance(s1, s2):
	"""returns the minimum number of substitution/insertion/deletion operations to
		transform string a into string b"""
	if len(s1) < len(s2):
		return levenshtein_distance(s2, s1)
	if len(s2) == 0:
		return len(s1)
	
	previous_row = range(len(s1) + 1)
	for i, c1 in enumerate(s1):
		current_row = [i + 1]
		for j, c2 in enumerate(s2):
			insertions = previous_row[j + 1] + 1
			deletions = current_row[j] + 1
			substitutions = previous_row[j] + (c1 != c2)
			current_row.append(min(insertions, deletions, substitutions))
		previous_row = current_row
	
	return previous_row[-1]


def create_alignments_from_edit_path(s1: str, s2: str, edit_path: List[int],
                                     alignment_symbol: str = "-") -> (str, str):
	"""path: 0 = sub, 1 = ins, 2 = del"""
	x, y = 0, 0
	aligned1, aligned2 = "", ""
	for operation in edit_path:
		if operation == 0:
			aligned1 += s1[x]
			aligned2 += s2[y]
			x, y = x + 1, y + 1
		elif operation == 1:
			aligned1 += alignment_symbol
			aligned2 += s2[y]
			y += 1
		else:
			assert operation == 2
			aligned1 += s1[x]
			aligned2 += alignment_symbol
			x += 1
	return aligned1, aligned2


def build_edit_distance_matrix(s1: str, s2: str, match_value=0, sub_value=-1, ins_value=-1, del_value=-1):
	swapped = False
	if len(s2) < len(s1):
		s1, s2 = s2, s1
		swapped = True
	la, lb = len(s1), len(s2)
	import numpy as np
	m = np.zeros((lb + 1, la + 1), dtype=np.int32)
	m[:] = -2 ** 30
	m[:, 0] = 0  # np.array([ins_value * x for x in range(la + 1)])
	m[0, :] = 0  # np.array([del_value * x for x in range(lb + 1)])
	s1ords = np.array([ord(it) for it in s1])
	s2ords = np.array([ord(it) for it in s2])
	for y in range(1, lb + 1):
		util.print_every_n_times(100, y, lb)
		for x in range(1, la + 1):
			sub = m[y - 1, x - 1] + (match_value if s1[x - 1] == s2[y - 1] else sub_value)
			ins = m[y - 1, x] + ins_value
			dels = m[y, x - 1] + del_value
			res = max(sub, ins, dels)
			m[y, x] = res
	if swapped:
		return m.transpose()
	return m


def compute_kmp_failure_array(pattern):
	result = [None]
	for i in range(0, len(pattern)):
		j = i
		while True:
			if j == 0:
				result.append(0)
				break
			if pattern[result[j]] == pattern[i]:
				result.append(result[j] + 1)
				break
			j = result[j]
	return result[1:]


def count_maxumum_number_of_substrings(alphabet_size, string_length):
	d, n = alphabet_size, string_length
	k = int(math.log(n, d))
	return (d ** (k + 1) - 1) // (d - 1) + ncr(n - k + 1, 2)
