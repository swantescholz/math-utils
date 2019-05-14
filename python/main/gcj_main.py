# uses python3.6
# (potentially) uses additional libraries:
# numpy, scipy, sympy, networkx, matplotlib, sortedcontainers
# you can install them with 'pip3 install <library-name>'
from typing import List, Callable

import matplotlib

matplotlib.use("TkAgg")
import numpy as np
import sys

import queue, random, time
from d3.ipair import ipair, NESW4
import resource
from ints import primes

P_max = 10 ** 2
input_name = "A-large"
input_name = "A-small-attempt0"
impossible = "IMPOSSIBLE"
input_name = "test"
input_name = "C-small-practice"
input_name = "C-large-practice"


def solve(gets: Callable[[], List[str]], tid: int):
	h, w = map(int, gets())
	grid = [list(" " * w) for _ in range(h)]
	walls, lasers, spaces = set(), dict(), set()
	amirrors, bmirrors = set(), set()
	for y in range(h - 1, -1, -1):
		line = gets()[0]
		for x, c in enumerate(line):
			grid[y][x] = c
			p = ipair(x, y)
			if c == "#":
				walls.add(p)
			if c == "/":
				amirrors.add(p)
			if c == "\\":
				bmirrors.add(p)
			if c == "-" or c == "|":
				lasers[p] = len(lasers) + 1
			if c == ".":
				spaces.add(p)
	ors = set()
	
	def follow(start, dir):
		assert start in spaces or start in lasers
		pos = start
		while True:
			pos += dir
			if pos in walls or not pos.in_range(w, h):
				return 1, None  # wall or outside
			if pos in lasers:
				return 3, (pos, dir)
			if pos in amirrors:
				dir = ipair(dir.y, dir.x)
			if pos in bmirrors:
				dir = ipair(-dir.y, -dir.x)
			if pos == start:
				return 2, None
	
	impossible_ids = set()
	for pos, ida in lasers.items():
		for dir in NESW4:
			a, b = follow(pos, dir)
			assert a in [1, 3]
			if a != 3:
				continue
			bpos, bdir = b
			if bpos in lasers:
				idb = lasers[bpos]
				if dir.x == 0:  # vertical bad
					ors.add(ipair(ida, ida))
					impossible_ids.add(-ida)
				else:
					ors.add(ipair(-ida, -ida))
					impossible_ids.add(ida)
	for pos in spaces:
		laser_ids = []
		for dir in NESW4:
			a, b = follow(pos, dir)
			assert a in [1, 2, 3]
			if a != 3:
				continue
			bpos, bdir = b
			if bpos in lasers:
				idb = lasers[bpos]
				if bdir.x == 0:  # vertical bad
					laser_ids.append(-idb)
				else:
					laser_ids.append(idb)
		laser_ids = [it for it in laser_ids if it not in impossible_ids]
		if len(laser_ids) == 0:
			return impossible
		elif len(laser_ids) == 1:
			ors.add((laser_ids[0], laser_ids[0]))
		elif len(laser_ids) == 2:
			ors.add((laser_ids[0], laser_ids[1]))
		else:
			assert False
	import algorithms.sat
	res = impossible[2:] + "\n"
	if len(lasers) > 0:
		solution = algorithms.sat.solve_2_sat(len(lasers), ors)
		if solution is None:
			return impossible
		for pos, id in lasers.items():
			if solution[id - 1] > 0:
				grid[pos.y][pos.x] = "-"
			else:
				grid[pos.y][pos.x] = "|"
	res += "\n".join("".join(it) for it in reversed(grid))
	return res


# ==============================================
def solve_all_test_cases(T, lines):
	primes.make_primes_up_to(P_max)
	q = queue.Queue()
	for line in lines:
		q.put(line.split())
	
	def q_get():
		return q.get()
	
	for test_case_id in range(1, T + 1):
		result = solve(q_get, test_case_id)
		if type(result) in {float, np.float32, np.float64}:
			result = f"{result:.8f}"
		text = "Case #{}: {}".format(test_case_id, result)
		write(text)


res_path = "../../../../downloads/"
output_path = res_path + input_name + ".out"
last_output = None
import os

if os.path.isfile(output_path):
	last_output = "".join(open(output_path).readlines())
current_output = ""
output = open(output_path, "w")


def mymain():
	input_lines = open(res_path + input_name + ".in").readlines()
	input_lines = [line.strip() for line in input_lines]
	T = int(input_lines[0])
	input_lines = input_lines[1:]
	solve_all_test_cases(T, input_lines)


def write(text):
	if text[-1] != "\n":
		text += "\n"
	print(text, end="", flush=True)
	global current_output
	current_output += text
	output.write(text)


if __name__ == "__main__":
	max_heap_size = 10 ** 9
	resource.setrlimit(resource.RLIMIT_DATA, (max_heap_size, max_heap_size))
	print(f"solving input file: {input_name}.in")
	start = time.time()
	random.seed(0)
	np.random.seed(0)
	sys.setrecursionlimit(2500)
	mymain()
	end = time.time()
	s = end - start
	m = int(s / 60)
	print("=" * 20)
	print(f"elapsed time: {s:.2f}s = {m}m {round(s)%60}s")
	print(f"results written to: {output_path}")
	if current_output == last_output:
		print("*****SAME*****")
	else:
		print("=====CHANGED=====")
