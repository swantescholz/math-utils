from typing import List, Union, Tuple, Set

import networkx as nx
import numpy as np


def maximum_independent_set_size_of_bipartite_graph(G: nx.Graph) -> int:
	"""nodes should have labels bipartite=0 and bipartite=1"""
	matching = nx.bipartite.maximum_matching(G)
	# vertex_cover = nx.bipartite.to_vertex_cover(G, matching)
	# independent_set = set(G) - vertex_cover
	# return independent_set
	return G.number_of_nodes() - len(matching) // 2


def all_pairs_shortest_distances(distance_matrix: np.ndarray) -> np.ndarray:
	"""floyd-warshall-like, all distances should be non-negative, negative means unconnected"""
	n = distance_matrix.shape[0]
	assert n == distance_matrix.shape[1]
	dist = np.array(distance_matrix)
	for x in range(n):
		dist[x, x] = 0
	for k in range(n):
		for y in range(n):
			for x in range(n):
				new_dist = dist[y, k] + dist[k, x]
				if dist[y, k] >= 0 and dist[k, x] >= 0 and (
								dist[y, x] < 0 or dist[y, x] > new_dist):
					dist[y, x] = dist[y, k] + dist[k, x]
	return dist


def color_graph_with_two_colors(adj: List[List[int]]) -> Union[None, Tuple[Set[int], Set[int]]]:
	"""takes undirected graph as adjacency lists (vertices implied by indices).
	:returns None if graph is not bipartit. returns the color partitioning otherwise"""
	sets = (set(), set())
	color = 0
	undone_nodes = set(range(len(adj)))
	while len(undone_nodes) > 0:
		neighbors = {undone_nodes.pop()}
		while len(neighbors) > 0:
			new_neighbors = set()
			for n in neighbors:
				sets[color].add(n)
				if n in undone_nodes:
					undone_nodes.remove(n)
				for new_n in adj[n]:
					if new_n in sets[color]:
						return None
					if new_n not in sets[1 - color]:
						new_neighbors.add(new_n)
			color = 1 - color
			neighbors = new_neighbors
	return sets
