from collections import defaultdict

from rosalind import misc


def read_fasta(orig_lines):
	"""returns list of pairs (id, dna)"""
	res = []
	id = ""
	dna = ""
	for line in orig_lines:
		if len(line) == 0:
			continue
		if line[0] == ">":
			if len(dna) > 0:
				res.append((id, dna))
			id = line[1:]
			dna = ""
		else:
			dna += line
	if len(dna) > 0:
		res.append((id, dna))
	return res


def hamming_distance(dna1, dna2):
	res = 0
	for a, b in zip(dna1, dna2):
		if a != b:
			res += 1
	return res


def compute_profile_matrix(dnas):
	profile = [[0] * len(dnas[0]) for i in range(4)]
	indices = dict([("A", 0), ("C", 1), ("G", 2), ("T", 3)])
	for dna in dnas:
		for i, c in enumerate(dna):
			profile[indices[c]][i] += 1
	return profile


def compute_consensus_string(profile_matrix):
	res = ""
	letters = "ACGT"
	for i in range(len(profile_matrix[0])):
		best_y = -1
		best_count = -1
		for y in range(4):
			if profile_matrix[y][i] > best_count:
				best_count = profile_matrix[y][i]
				best_y = y
		res += letters[best_y]
	return res


def reverse_complement(dna):
	dna = "".join(reversed(dna)).lower()
	dna = dna.replace("a", "T")
	dna = dna.replace("t", "A")
	dna = dna.replace("c", "G")
	dna = dna.replace("g", "C")
	return dna


def read_fasta_from_url(url):
	text = misc.read_text_from_url(url)
	dnas = read_fasta(text.split("\n"))
	return dnas


def transition_transversion_ration(dna1, dna2):
	transitions, transversions = 0, 0
	for a, b in zip(dna1, dna2):
		if a == b:
			continue
		t = tuple(sorted([a, b]))
		if t == ("A", "G") or t == ("C", "T"):
			transitions += 1
		else:
			transversions += 1
	return transitions / transversions


def reversal_distance(perm1, perm2):
	def neighbors(perm):
		neighbor_list = set()
		for i in range(len(perm) - 1):
			for j in range(i + 1, len(perm)):
				neighbor_list.add(perm[:i] + tuple(reversed(perm[i:j + 1])) + perm[j + 1:])
		return neighbor_list
	
	if perm1 == perm2:
		return 0
	adj1, adj2 = {perm1}, {perm2}
	distance = 0
	done = set()
	for i in range(len(perm1)):
		new_adj1, new_adj2 = set(), set()
		for node in adj1:
			if node in adj2:
				return distance * 2
			done.add(node)
			for neighbor in neighbors(node):
				if neighbor not in done:
					if neighbor in adj2:
						return distance * 2 + 1
					new_adj1.add(neighbor)
		for node in adj2:
			done.add(node)
			for neighbor in neighbors(node):
				if neighbor not in done:
					if neighbor in new_adj1:
						return distance * 2 + 2
					new_adj2.add(neighbor)
		print(len(done), len(new_adj1), len(new_adj2))
		distance += 1
		adj1, adj2 = new_adj1, new_adj2
	assert False


def reversal_sort(perm1, perm2):
	"""returns distance and permutation path"""
	parents = dict()
	def find():
		def neighbors(perm):
			neighbor_list = set()
			for i in range(len(perm) - 1):
				for j in range(i + 1, len(perm)):
					neighbor_list.add(perm[:i] + tuple(reversed(perm[i:j + 1])) + perm[j + 1:])
			return neighbor_list
		
		if perm1 == perm2:
			return 0
		adj1, adj2 = {perm1}, {perm2}
		distance = 0
		done = set()
		for i in range(len(perm1)):
			new_adj1, new_adj2 = set(), set()
			for node in adj1:
				if node in adj2:
					assert False
					# return distance * 2
				done.add(node)
				for neighbor in neighbors(node):
					if neighbor not in done and neighbor not in adj1:
						if neighbor in adj2:
							return distance * 2 + 1, neighbor, node
						parents[neighbor] = node
						new_adj1.add(neighbor)
			for node in adj2:
				done.add(node)
				for neighbor in neighbors(node):
					if neighbor not in done and neighbor not in adj2:
						if neighbor in new_adj1:
							return distance * 2 + 2, neighbor, node
						parents[neighbor] = node
						new_adj2.add(neighbor)
			print(len(done), len(new_adj1), len(new_adj2))
			distance += 1
			adj1, adj2 = new_adj1, new_adj2
		assert False
	distance, middle, second_parent = find()
	path = [second_parent]
	while second_parent in parents:
		second_parent = parents[second_parent]
		path.append(second_parent)
	path.reverse()
	path.append(middle)
	while middle in parents:
		middle = parents[middle]
		path.append(middle)
	if path[0] != perm1:
		path.reverse()
	return distance, path
