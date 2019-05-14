from typing import Optional, Tuple

from d3.vec2 import vec2


class rectangle:
	def __init__(self, w: float, h: float, x: float = 0.0, y: float = 0.0):
		self.w: float = w
		self.h: float = h
		self.x: float = x
		self.y: float = y
	
	def __eq__(self, other: "rectangle"):
		if not isinstance(other, rectangle):
			return False
		return self.x == other.x and self.y == other.y and (
			self.w == other.w and self.h == other.h
		)
	
	def __hash__(self):
		return hash((self.w, self.h, self.x, self.y))
	
	def intersection(self, other: "rectangle") -> Optional["rectangle"]:
		if self.x > other.x + other.w or other.x > self.x + self.w:
			return None
		if self.y > other.y + other.h or other.y > self.y + self.h:
			return None
		x, y = max(self.x, other.x), max(self.y, other.y)
		w = min(self.x + self.w - other.x, other.x + other.w - self.x)
		h = min(self.y + self.h - other.y, other.y + other.h - self.y)
		return rectangle(w, h, x, y)
	
	def __str__(self):
		return f"({self.w}, {self.h}, {self.x}, {self.y})"
	
	def __repr__(self):
		return str(self)
	
	def __contains__(self, item: vec2):
		return self.x <= item.x <= self.x + self.w and self.y <= item.y <= self.y + self.h
	
	def intersect_ray(self, line_start: vec2, line_direction: vec2) -> Tuple[
		Optional[float], Optional[float]]:
		""":returns the two times t1, t2 (possibly None), where the ray collides with the
		rectangle. if start is in the rectancle, t1 will be None"""
		ts = []
		a, b = line_start
		c, d = line_direction
		if c != 0:
			t1, t2 = (self.x - a) / c, (self.x + self.w - a) / c
			if self.y < (b + d * t1) < self.y + self.h:
				ts.append(t1)
			if self.y < (b + d * t2) < self.y + self.h:
				ts.append(t2)
		if d != 0:
			t1, t2 = (self.y - b) / d, (self.y + self.h - b) / d
			if self.x <= (a + c * t1) <= self.x + self.w:
				ts.append(t1)
			if self.x <= (a + c * t2) <= self.x + self.w:
				ts.append(t2)
		ts = sorted([t for t in ts if t >= 0.0 and
		             (line_start + t * line_direction) in self])
		if len(ts) == 0:
			return None, None
		if len(ts) == 1:
			return None, ts[0]
		return ts[-2], ts[-1]
	
	@staticmethod
	def from_points(a: vec2, b: vec2) -> "rectangle":
		xy = a.min_componentwise(b)
		wh = a.max_componentwise(b) - xy
		return rectangle.from_wh_xy(wh, xy)
	
	@staticmethod
	def from_wh_xy(wh: vec2, xy: vec2 = vec2(0, 0)) -> "rectangle":
		return rectangle(wh.x, wh.y, xy.x, xy.y)


if __name__ == "__main__":
	a = rectangle(5, 6, 1, 2)
	b = rectangle(6, 3, 3, 0)
	assert a.intersection(b) == rectangle(3, 1, 3, 2)
	assert (2.5, None) == a.intersect_ray(vec2(3, 3), vec2(1, 2))
	assert (0.5, 2) == b.intersect_ray(vec2(7, 4), vec2(-1, -2))
