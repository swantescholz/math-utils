from typing import Generic, TypeVar, List

T = TypeVar('T')


class Tree(Generic[T]):
	class Node(Generic[T]):
		def __init__(self, key: T, children: List["Tree.Node"] = list(),
		             parent: "Tree.Node" = None):
			self.key: T = key
			self.children: List["Tree.Node"] = children
			self.parent: "Tree.Node" = parent
	
	def __init__(self, root: Node = None):
		self.root: Tree.Node = root


if __name__ == "__main__":
	tree = Tree()
