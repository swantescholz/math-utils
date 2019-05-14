import math


class vec2:
	def __init__(self, x: float = 0.0, y: float = 0.0):
		self.x: float = x
		self.y: float = y
	
	def length(self) -> float:
		return math.sqrt(self.x * self.x + self.y * self.y)
	
	def length2(self) -> float:
		return self.x * self.x + self.y * self.y
	
	def dot(self, you: "vec2") -> float:
		return self.x * you.x + self.y * you.y
	
	def distance_to(self, other: "vec2") -> float:
		return (self - other).length()
	
	def distance_to2(self, other: "vec2") -> float:
		return (self - other).length2()
	
	def __neg__(self) -> "vec2":
		return vec2(-self.x, -self.y)
	
	def __add__(self, other: "vec2") -> "vec2":
		return vec2(self.x + other.x, self.y + other.y)
	
	def __sub__(self, other: "vec2") -> "vec2":
		return vec2(self.x - other.x, self.y - other.y)
	
	def __mul__(self, other: float) -> "vec2":
		return vec2(self.x * other, self.y * other)
	
	def __truediv__(self, other: float) -> "vec2":
		return self * (1.0 / other)
	
	def __rmul__(self, other: float) -> "vec2":
		return vec2(self.x * other, self.y * other)
	
	def normalized(self) -> "vec2":
		return self / self.length()
	
	def angle(self, other: "vec2") -> float:
		return math.acos(self.dot(other) / math.sqrt(self.length2() * other.length2()))
	
	def __str__(self):
		return f"({self.x}, {self.y})"
	
	def __repr__(self):
		return str(self)
	
	def with_length(self, new_length: float) -> "vec2":
		return self * (new_length / self.length())
	
	def __iter__(self):
		return iter((self.x, self.y))
	
	def min_componentwise(self, other: "vec2") -> "vec2":
		return vec2(min(self.x, other.x), min(self.y, other.y))
	
	def max_componentwise(self, other: "vec2") -> "vec2":
		return vec2(max(self.x, other.x), max(self.y, other.y))
