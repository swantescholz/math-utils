from d3.vec3 import vec3


class plane:
	def __init__(self, n: vec3, d: float = 0.0):
		self.n: vec3 = n
		self.d: float = d
	
	def __str__(self):
		return f"({self.n}, {self.d})"
	
	def distance_to_point(self, point: vec3) -> float:
		return self._dot(point)
	
	def _dot(self, b: vec3) -> float:
		return self.n.dot(b) + self.d
	
	def nearest_point(self, point: vec3) -> vec3:
		""":returns point on plane with lowest distance to given point"""
		return -self.n * self.distance_to_point(point) + point
	
	@staticmethod
	def from_points(a: vec3, b: vec3, c: vec3) -> "plane":
		n = (b - a).cross(c - a).normalized()
		d = -n.dot(a)
		return plane(n, d)
