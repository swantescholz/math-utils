import math
from typing import Optional

from d3.vec3 import vec3
from util import ast_equals


def ray_hits_zero_sphere(ray_start: vec3, ray_dir: vec3, sphere_radius: float) -> (
		Optional[float], Optional[float]):
	""":returns times of intersection of ray with sphere centered at (0,0,0)"""
	A, B = ray_start, ray_dir
	discr = (A.dot(B) / B.length2()) ** 2 + (sphere_radius ** 2 - A.length2()) / B.length2()
	base = -A.dot(B) / B.length2()
	if discr < 0:
		return None, None
	if discr == 0:
		return base, base
	discr = math.sqrt(discr)
	t1, t2 = base - discr, base + discr
	if t1 < 0:
		t1 = None
	return t1, t2


if __name__ == "__main__":
	a = vec3(5, 4, 0)
	b = vec3(-4, 0, 0)
	t1, t2 = ray_hits_zero_sphere(a, b, 5)
	ast_equals(t1, 0.5)
	ast_equals(t2, 2.0)
