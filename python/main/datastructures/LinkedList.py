from typing import Generic, TypeVar

T = TypeVar('T')


class Node(Generic[T]):
	def __init__(self, data: T):
		self.data: T = data
		self.next: "Node[T]" = None
		self.prev: "Node[T]" = None
	
	def __str__(self):
		return str(self.data)


class _LinkedListIterator(Generic[T]):
	def __init__(self, head: Node[T]):
		self.next_node: Node[T] = head
	
	def __next__(self) -> Node[T]:
		if self.next_node is None:
			raise StopIteration
		res = self.next_node
		self.next_node = self.next_node.next
		return res


class LinkedList(Generic[T]):
	def __init__(self):
		self.head: Node[T] = None
		self.tail: Node[T] = None
		self.size: int = 0
	
	def __len__(self):
		return self.size
	
	def __iter__(self):
		return _LinkedListIterator(self.head)
	
	def add_right(self, data: T):
		new_node = Node(data)
		if self.head is None:
			self.head = new_node
			self.tail = new_node
		else:
			self.tail.next = new_node
			new_node.prev = self.tail
			self.tail = new_node
		self.size += 1
	
	def remove(self, node):
		self.size -= 1
		if node is self.head:
			self.head = self.head.next
		if node is self.tail:
			self.tail = self.tail.prev
		if node.prev is not None:
			node.prev.next = node.next
		if node.next is not None:
			node.next.prev = node.prev
		node.prev = None
		node.next = None
	
	def __str__(self) -> str:
		items = []
		node = self.head
		while node is not None:
			items.append(str(node.data))
			node = node.next
		return "[" + ", ".join(items) + "]"


if __name__ == "__main__":
	l = LinkedList()
	for c in "abcdef":
		l.add_right(c)
	print(l)
	for node in l:
		if node.data in "bce":
			node.remove()
	print(l)
