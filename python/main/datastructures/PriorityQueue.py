from sortedcontainers import SortedSet


class PriorityQueue:
	"""min first"""
	
	def __init__(self):
		self._priorities_with_counter = dict()
		self._tree = SortedSet()
		self._counter = 0
	
	def add_or_update(self, value, priority):
		if value in self._priorities_with_counter:
			self._tree.remove((self._priorities_with_counter[value], value))
			del self._priorities_with_counter[value]
		self._priorities_with_counter[value] = (priority, self._counter)
		self._tree.add(((priority, self._counter), value))
		self._counter += 1
	
	def get_priority(self, value):
		return self._priorities_with_counter[value][0]
	
	def remove_if_present(self, value):
		if value in self._priorities_with_counter:
			self._tree.remove((self._priorities_with_counter[value], value))
			del self._priorities_with_counter[value]
	
	def __len__(self):
		return len(self._priorities_with_counter)
	
	def pop_first(self):
		""":returns (value, priority) of element with the lowest priority"""
		assert len(self) > 0, "priority queue is empty!"
		tmp, value = self._tree.pop(0)
		priority, _ = tmp
		del self._priorities_with_counter[value]
		return value, priority


if __name__ == "__main__":
	print("testing priority queue")
	q = PriorityQueue()
	q.add_or_update(3, 3)
	q.add_or_update(5, 2)
	print(q.pop_first())
	q.add_or_update(6, 2)
	q.add_or_update(3, 1)
	print(q.pop_first())
	print(len(q))
	print(q.pop_first())
	print(len(q))
