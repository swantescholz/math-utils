from collections import defaultdict
from typing import List

import networkx as nx


def read_newick_tree(s: str):
	if s[-1] == ";":
		s = s[:-1]
	noname_index = 0
	
	def build(s: str):
		"""returns rootname, [children]"""
		nonlocal noname_index
		count_open = s.count("(")
		count_closing = s.count(")")
		assert count_open == count_closing
		if count_open == 0:
			assert "," not in s
			if s == "":
				s = f"__{noname_index}"
				noname_index += 1
			return s, []
		a = s.find("(")
		b = s.rfind(")")
		diff = 0
		last_comma_index = a
		parts = []
		for i in range(a + 1, b):
			if s[i] == "," and diff == 0:
				parts.append(s[last_comma_index + 1:i])
				last_comma_index = i
			if s[i] == "(":
				diff += 1
			if s[i] == ")":
				diff -= 1
		parts.append(s[last_comma_index + 1:b])
		name = s[b + 1:]
		if name == "":
			name = f"__{noname_index}"
			noname_index += 1
		return name, [build(it) for it in parts]
	
	root = build(s)
	return root
	# g = nx.Graph()
	#
	# def f2nx(x):
	# 	for child in x[1]:
	# 		g.add_edge(x[0], child[0])
	# 		f2nx(child)
	#
	# f2nx(root)
	# return g


def read_newick_tree_with_weights(s: str):
	if s[-1] == ";":
		s = s[:-1]
	noname_index = 0
	
	def get_name(name):
		nonlocal noname_index
		if name == "":
			name = f"__{noname_index}"
			noname_index += 1
		return name
	
	def build(s: str):
		count_open = s.count("(")
		count_closing = s.count(")")
		assert count_open == count_closing
		if count_open == 0:
			assert "," not in s
			if ":" not in s:
				return get_name(s), 0.0, []
			name, weight = s.split(":")
			weight = float(weight)
			return get_name(name), weight, []
		a = s.find("(")
		b = s.rfind(")")
		diff = 0
		last_comma_index = a
		parts = []
		for i in range(a + 1, b):
			if s[i] == "," and diff == 0:
				parts.append(s[last_comma_index + 1:i])
				last_comma_index = i
			if s[i] == "(":
				diff += 1
			if s[i] == ")":
				diff -= 1
		parts.append(s[last_comma_index + 1:b])
		children = [build(it) for it in parts]
		back = s[b + 1:]
		if ":" not in back:
			return get_name(back), 0.0, children
		name, weight = back.split(":")
		weight = float(weight)
		return get_name(name), weight, children
	
	root = build(s)
	g = nx.Graph()
	
	def f2nx(x):
		for child in x[2]:
			g.add_edge(x[0], child[0], weight=child[1])
			f2nx(child)
	
	f2nx(root)
	return g


def print_newick_tree_without_inner_nodes(g):
	g = g.copy()
	
	def f(node):
		if len(g.neighbors(node)) == 0:
			return node
		res = []
		for child in list(g.neighbors(node)):
			g.remove_edge(node, child)
			res.append(f(child))
		g.remove_node(node)
		return "(" + ",".join(str(it) for it in res) + ")"
	
	root = sorted(it for it in g.nodes() if len(g.neighbors(it)) == 1)[0]
	start = g.neighbors(root)[0]
	return f"{f(start)};"


def build_tree_from_character_table(leaf_names, split_table):
	noname_index = 0
	
	def get_name(name):
		nonlocal noname_index
		if name == "":
			name = f"__{noname_index}"
			noname_index += 1
		return name
	
	tree = nx.Graph()
	
	def build(root, remaining_names, remaining_splits):
		remaining_names = list(remaining_names)
		remaining_splits.sort(key=lambda it: min(it.count("0"), it.count("1")), reverse=True)  # crucial! for getting right splitting
		if len(remaining_names) == 0:
			tree.remove_node(root)
			return
		if len(remaining_splits) == 0:
			if len(remaining_names) == 1:
				parent = tree.neighbors(root)[0]
				tree.remove_node(root)
				tree.add_edge(parent, remaining_names[0])
				return
			node = root
			for name in remaining_names[:-2]:
				new_node = get_name("")
				tree.add_edge(node, name)
				tree.add_edge(node, new_node)
				node = new_node
			tree.add_edge(node, remaining_names[-2])
			tree.add_edge(node, remaining_names[-1])
			return
		split = set(leaf_names[a] for a, b in enumerate(remaining_splits[0]) if b == "0")
		sa = split.intersection(remaining_names)
		sb = set(remaining_names) - split
		
		remaining_splits = remaining_splits[1:]
		if len(sa) == 0 or len(sb) == 0:
			build(root, remaining_names, remaining_splits)
			return
		# assert len(split - set(names)) == 0 or len((set(leaf_names) - split) - set(names)) == 0
		roota = get_name("")
		rootb = get_name("")
		tree.add_edge(root, roota)
		tree.add_edge(root, rootb)
		build(roota, sa, remaining_splits)
		build(rootb, sb, remaining_splits)
	
	root = get_name("")
	build(root, leaf_names, split_table)
	a, b = tree.neighbors(root)
	tree.add_edge(a, b)
	tree.remove_node(root)
	return tree


def build_trie(words: List[str]):
	root = 1
	adj = defaultdict(dict)
	node_index = 1
	for word in words:
		node = root
		for letter in word:
			if letter not in adj[node]:
				node_index += 1
				adj[node][letter] = node_index
			node = adj[node][letter]
	return adj

