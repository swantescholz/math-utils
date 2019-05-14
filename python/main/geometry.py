from typing import List, Tuple


def is_point_left_of_line(start_of_line, end_of_line, point):
	a = end_of_line - start_of_line
	b = point - start_of_line
	return a[0] * b[1] - a[1] * b[0] > 0


def simple_polygon_area(vertices: List[Tuple[float, float]]) -> float:
	v = vertices
	return 0.5 * abs(v[-1][0] * v[0][1] - v[0][0] * v[-1][1] +
	                 sum(v[i][0] * v[i + 1][1] - v[i + 1][0] * v[i][1] for i in range(len(v) - 1)))
