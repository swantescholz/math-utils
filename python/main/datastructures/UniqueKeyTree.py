import random
from collections import defaultdict
from typing import Generic, TypeVar, List, Dict

T = TypeVar('T')


class UniqueKeyTree(Generic[T]):
	"""don't change tree structure after init"""
	
	def __init__(self, parents: Dict[T, T]):
		self.parents: Dict[T, T] = dict(parents)
		self.children: Dict[T, List[T]] = defaultdict(list)
		for child, parent in self.parents.items():
			self.children[parent].append(child)
		self._subtree_sizes: Dict[T, int] = defaultdict(int)
	
	def subtree_size(self, subtree_root: T) -> int:
		if subtree_root in self._subtree_sizes:
			return self._subtree_sizes[subtree_root]
		res = 1 + sum(self.subtree_size(it) for it in self.children[subtree_root])
		self._subtree_sizes[subtree_root] = res
		return res
	
	def generate_uniform_random_topological_ordering(self) -> List[T]:
		res = []
		candidates = [parent for parent in self.children if parent not in self.parents]
		while len(candidates) > 0:
			counts = [self.subtree_size(it) for it in candidates]
			counts_sum = sum(counts)
			counts = [it / counts_sum for it in counts]
			i, = random.choices(candidates, counts)
			res.append(i)
			candidates.remove(i)
			for it in self.children[i]:
				candidates.append(it)
		return res


if __name__ == "__main__":
	d = {2: 0, 1: 0, 3: 1, 5: 1, 6: 2, 7: 2, 8: 5, 9: 5}
	t = UniqueKeyTree(d)
	assert t.subtree_size(1) == 5
	t = UniqueKeyTree({1: 0, 2: 0, 3: 1, 4: 1, 5: 3})
	dic = defaultdict(int)
	for _ in range(1000):
		topo = t.generate_uniform_random_topological_ordering()
		topo = "".join(str(it) for it in topo)
		dic[topo] += 1
	for k, v in dic.items():
		print(k, v)  # counts should be similar
	print(f"dic size: {len(dic)}")
