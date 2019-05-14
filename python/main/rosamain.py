# uses python3.6
# (potentially) uses additional libraries:
# numpy, scipy, sympy, networkx, matplotlib, sortedcontainers
# you can install them with 'pip3 install <library-name>'
import itertools
import matplotlib

import rosalind
from rosalind.constants import PAM250, BLOSUM62
from rosalind.strings import build_edit_distance_matrix
from rosalind.suffix_tree import SuffixTree

matplotlib.use("TkAgg")
import collections
import math, itertools
from collections import defaultdict
from pprint import pprint
import networkx as nx

from ints import primes
from ints.misc import ncr
from rosalind.misc import myjoin
from rosalind import trees, strings, suffix_tree, Ps
from rosalind import Dna
from util import print_regularly

# import pandas as pd
# import numpy as np
import matplotlib.pyplot as plt
import sys
from decimal import *
getcontext().prec = 18

import queue, random, time
import resource, re

res_path = "../../../../downloads/"
P_max = 10 ** 2
mod = 10 ** 6

input_name = "rosalind_foobar"
# input_name += " (1)"
# input_name = "test"

class Node:
	def __init__(self, parent, children, id):
		self.parent = parent
		self.children = children
		self.id = id
		self.leaf_set = set()
		

def solve(gets, lines):
	names_dic = dict((b,a)for a,b in enumerate(lines[0].split()))
	ta = trees.read_newick_tree(lines[1])
	tb = trees.read_newick_tree(lines[2])
	
	def fa(t):
		assert len(t[1]) == 3
		return "", [(t[0], t[1][:2]), t[1][2]]
	
	ta, tb = fa(ta), fa(tb)
	leaf_dics = [dict(), dict()]
	def fb(t, parent, index):
		if len(t[1]) == 0:
			leaf_id = names_dic[t[0]]
			leaf = Node(parent, [], leaf_id)
			leaf.leaf_set = {leaf_id}
			leaf_dics[index][leaf_id] = leaf
			return leaf
		node = Node(parent, [], -1)
		for child in t[1]:
			new_child_node = fb(child, node, index)
			node.children.append(new_child_node)
			node.leaf_set.update(new_child_node.leaf_set)
		return node
	ta, tb = fb(ta, None, 0), fb(tb, None, 1)
	res = 0
	def fd(index,a,b):
		node = leaf_dics[index][a]
		sets = []
		while True:
			if len(node.children) > 0:
				assert len(node.children) == 2
				if any(b in child.leaf_set for child in node.children):
					break
			for child in node.children:
				if a not in child.leaf_set:
					sets.append(child.leaf_set)
			node = node.parent
		# if node.parent is not None:
			# sets.append(node.) # todo
		while True:
			if node.id == b:
				break
			for child in node.children:
				if b in child.leaf_set:
					for other_child in node.children:
						if b not in other_child.leaf_set and a not in other_child.leaf_set:
							sets.append(other_child.leaf_set)
					node = child
					break
		return sets
	def fc(a,b):
		nonlocal res
		setsa, setsb = fd(0,a,b), fd(1,a,b)
		ids = [[-1 for _ in range(len(names_dic))] for _ in range(2)]
		for index, sets in enumerate([setsa, setsb]):
			for set_id, the_set in enumerate(sets):
				for number in the_set:
					ids[index][number] = set_id
		counts = defaultdict(int)
		for i in range(len(names_dic)):
			if ids[0][i] != -1 and ids[1][i] != -1:
				counts[ids[0][i], ids[1][i]] += 1
		for it in counts.values():
			res += it*(it-1) // 2
	for a in range(len(names_dic)):
		print(a)
		for b in range(a+1, len(names_dic)):
			fc(a,b)
	ncra = ncr(len(ta.leaf_set), 4)
	ncrb = ncr(len(tb.leaf_set), 4)
	final_res = ncra + ncrb - 2* res
	print(final_res, ncra, ncrb, res, len(ta.leaf_set))




# ==============================================
def solve_all_test_cases(lines, orig_lines):
	primes.make_primes_up_to(P_max)
	q = queue.Queue()
	for line in lines:
		q.put(line.split())
	
	def q_get():
		return q.get()
	
	solve(q_get, orig_lines)

output_path = res_path + input_name + ".out"
last_output = None
import os

if os.path.isfile(output_path):
	last_output = "".join(open(output_path).readlines())
current_output = ""
output = open(output_path, "w")


def mymain():
	orig_lines = open(res_path + input_name + ".txt").readlines()
	orig_lines = [it[:-1] if it[-1] == "\n" else it for it in orig_lines]
	input_lines = [line.strip() for line in orig_lines]
	input_lines = input_lines[0:]
	solve_all_test_cases(input_lines, orig_lines)


def write(*args):
	text = " ".join(str(it) for it in args)
	if len(text) > 0 and text[-1] != "\n":
		text += "\n"
	if len(text) < 5000:
		print(text, end="", flush=True)
	else:
		print(f"<text too long to print ({len(text)} chars), written to file instead>")
	global current_output
	current_output += text
	output.write(text)


if __name__ == "__main__":
	max_heap_size = 10 ** 9
	resource.setrlimit(resource.RLIMIT_DATA, (max_heap_size, max_heap_size))
	print(f"solving input file: {input_name}.txt")
	print("=" * 50)
	start = time.time()
	random.seed(0)
	# np.random.seed(0)
	sys.setrecursionlimit(2500)
	mymain()
	end = time.time()
	s = end - start
	m = int(s / 60)
	print("=" * 50)
	print(f"elapsed time: {s:.2f}s = {m}m {round(s)%60}s")
	print(f"results written to: {output_path}")
	if current_output == last_output:
		print("*****SAME*****")
	else:
		print("=====CHANGED=====")
