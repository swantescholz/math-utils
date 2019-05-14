from typing import TypeVar, Generic, Dict

import numpy as np

T = TypeVar('T')


class DisjointSets(Generic[T]):
	def __init__(self):
		self._roots: Dict[T, T] = {}
		self._weights: Dict[T, int] = {}
	
	def number_of_sets(self):
		return len(np.unique(list(self._roots.values())))
	
	def total_number_of_elements(self):
		return len(self._roots)
	
	def add(self, element: T) -> None:
		self[element]
	
	def __getitem__(self, element: T):
		""":returns the representative of the set containing the element"""
		if element not in self._roots:
			self._roots[element] = element
			self._weights[element] = 1
			return element
		
		path = [element]
		root = self._roots[element]
		while root != path[-1]:
			path.append(root)
			root = self._roots[root]
		
		for parent in path:
			self._roots[parent] = root
		return root
	
	def merge(self, elements) -> None:
		"""merges the sets the elements belong to"""
		local_roots = set(self[x] for x in elements)
		root_with_max_weight = max([(self._weights[r], r) for r in local_roots])[1]
		for r in local_roots:
			if r != root_with_max_weight:
				self._weights[root_with_max_weight] += self._weights[r]
				self._roots[r] = root_with_max_weight
	
	def __iter__(self):
		return iter(self._roots)
