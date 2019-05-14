import math


class vec3:
	def __init__(self, x: float = 0.0, y: float = 0.0, z: float = 0.0):
		self.x: float = x
		self.y: float = y
		self.z: float = z
	
	def length(self) -> float:
		return math.sqrt(self.x * self.x + self.y * self.y + self.z * self.z)
	
	def length2(self) -> float:
		return self.x * self.x + self.y * self.y + self.z * self.z
	
	def dot(self, you: "vec3") -> float:
		return self.x * you.x + self.y * you.y + self.z * you.z
	
	def distance_to(self, other: "vec3") -> float:
		return (self - other).length()
	
	def distance_to2(self, other: "vec3") -> float:
		return (self - other).length2()
	
	def __neg__(self) -> "vec3":
		return vec3(-self.x, -self.y, -self.z)
	
	def __add__(self, other: "vec3") -> "vec3":
		return vec3(self.x + other.x, self.y + other.y, self.z + other.z)
	
	def __sub__(self, other: "vec3") -> "vec3":
		return vec3(self.x - other.x, self.y - other.y, self.z - other.z)
	
	def __mul__(self, other: float) -> "vec3":
		return vec3(self.x * other, self.y * other, self.z * other)
	
	def __truediv__(self, other: float) -> "vec3":
		return self * (1.0 / other)
	
	def __rmul__(self, other: float) -> "vec3":
		return vec3(self.x * other, self.y * other, self.z * other)
	
	def normalized(self) -> "vec3":
		return self / self.length()
	
	def angle(self, other: "vec3") -> float:
		return math.acos(self.dot(other) / math.sqrt(self.length2() * other.length2()))
	
	def cross(self, other: "vec3") -> "vec3":
		new_x = self.y * other.z - self.z * other.y
		new_y = self.z * other.x - self.x * other.z
		new_z = self.x * other.y - self.y * other.x
		return vec3(new_x, new_y, new_z)
	
	def __str__(self):
		return f"({self.x}, {self.y}, {self.z})"
	
	def __repr__(self):
		return str(self)
	
	def make_perpendicular_to(self, other: "vec3") -> "vec3":
		old_length = self.length()
		return other.cross(self).cross(other).with_length(old_length)
	
	def with_length(self, new_length: float) -> "vec3":
		return self * (new_length / self.length())
	
	def __iter__(self):
		return iter((self.x, self.y, self.z))
