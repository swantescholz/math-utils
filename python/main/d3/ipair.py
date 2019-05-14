import math

from d3.vec2 import vec2


class ipair:
	def __init__(self, x=0, y=0):
		self.x: int = x
		self.y: int = y
	
	def in_range(self, w, h):
		""":returns if 0<=x<w and 0<=y<h"""
		return 0 <= self.x < w and 0 <= self.y < h
	
	def sorted(self) -> "ipair":
		if self.x <= self.y:
			return ipair(self.x, self.y)
		return ipair(self.y, self.x)
	
	def max(self) -> int:
		return max(self.x, self.y)
	
	def min(self) -> int:
		return min(self.x, self.y)
	
	def to_vec2(self) -> vec2:
		return vec2(float(self.x), float(self.y))
	
	def max_componentwise(self, other: "ipair"):
		return ipair(max(self.x, other.x), max(self.y, other.y))
	
	def min_componentwise(self, other: "ipair"):
		return ipair(min(self.x, other.x), min(self.y, other.y))
	
	def length(self) -> float:
		return math.sqrt(self.x * self.x + self.y * self.y)
	
	def manhattan_length(self) -> int:
		return abs(self.x) + abs(self.y)
	
	def length2(self) -> int:
		return self.x * self.x + self.y * self.y
	
	def distance_to(self, other: "ipair") -> float:
		return (self - other).length()
	
	def distance_to2(self, other: "ipair") -> int:
		return (self - other).length2()
	
	def manhattan_distance_to(self, other: "ipair"):
		return (self - other).manhattan_length()
	
	def __getitem__(self, item: int):
		assert 0 <= item <= 1
		if item == 0:
			return self.x
		return self.y
	
	def __str__(self):
		return f"({self.x}, {self.y})"
	
	def __repr__(self):
		return f"({self.x}, {self.y})"
	
	def __eq__(self, other: "ipair") -> bool:
		if not isinstance(other, ipair):
			return False
		return self.x == other.x and self.y == other.y
	
	def __lt__(self, other: "ipair") -> bool:
		return self.x < other.x or self.x == other.y and self.y < other.y
	
	def __le__(self, other: "ipair") -> bool:
		return self.x < other.x or self.x == other.y and self.y <= other.y
	
	def __gt__(self, other: "ipair") -> bool:
		return self.x > other.x or self.x == other.y and self.y > other.y
	
	def __ge__(self, other: "ipair") -> bool:
		return self.x > other.x or self.x == other.y and self.y >= other.y
	
	def __hash__(self):
		return hash((self.x, self.y))
	
	def __neg__(self) -> "ipair":
		return ipair(-self.x, -self.y)
	
	def __add__(self, other: "ipair") -> "ipair":
		return ipair(self.x + other.x, self.y + other.y)
	
	def __sub__(self, other: "ipair") -> "ipair":
		return ipair(self.x - other.x, self.y - other.y)
	
	def __mul__(self, other: int) -> "ipair":
		return ipair(self.x * other, self.y * other)
	
	def __floordiv__(self, other: int) -> "ipair":
		return ipair(self.x // other, self.y // other)
	
	def __rmul__(self, other: int) -> "ipair":
		return ipair(self.x * other, self.y * other)
	
	def rotated_right(self) -> "ipair":
		""":returns copy of self rotated by 90 degrees clockwise"""
		return ipair(self.y, -self.x)
	
	def rotated_left(self) -> "ipair":
		""":returns copy of self rotated by 90 degrees counter-clockwise"""
		return ipair(-self.y, self.x)
	
	def __iter__(self):
		return iter((self.x, self.y))
	
	def is_collinear_to(self, other: "ipair") -> bool:
		return self.x * other.y == self.y * other.x


NESW4 = [ipair(0, 1), ipair(1, 0), ipair(0, -1), ipair(-1, 0)]
NESW8 = [ipair(0, 1), ipair(1, 1), ipair(1, 0), ipair(1, -1),
         ipair(0, -1), ipair(-1, -1), ipair(-1, 0), ipair(-1, 1)]

if __name__ == "__main__":
	a = ipair(2, 3)
	x, y = a
	assert x == 2
	assert y == 3
