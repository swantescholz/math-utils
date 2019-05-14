from typing import List, Tuple

import networkx as nx


def solve_2_sat(num_variables: int, or_pairs: List[Tuple[int, int]]) -> List[int]:
	"""takes or clauses (2-CNF), returns ids of true values, sorted by absolute id.
	negative ids indicate negations,
	e.g. 3, [(-1,2),(-2,3),(-3,-1)] -> [-1,2,3]"""
	g = nx.DiGraph()
	for n in range(1, num_variables + 1):
		g.add_node(n)
		g.add_node(-n)
	for a, b in or_pairs:
		assert 1 <= abs(a) <= num_variables and 1 <= abs(b) <= num_variables
		g.add_edge(-a, b)
		g.add_edge(-b, a)
	sccs = list(nx.strongly_connected_components(g))
	for s in sccs:
		for node in s:
			if (-node) in s:
				return None
	g_condensed = nx.condensation(g, scc=sccs)
	top_sorted_scc_ids = nx.topological_sort(g_condensed, reverse=True)
	truths = set()
	for scc_id in top_sorted_scc_ids:
		scc = sccs[scc_id]
		for node in scc:
			if (-node) not in truths:
				truths.add(node)
	assert len(truths) == num_variables
	res = list()
	for n in range(1, num_variables + 1):
		if n in truths:
			res.append(n)
		else:
			res.append(-n)
	return res


if __name__ == "__main__":
	ors = [(-1, 2), (-2, 3), (-3, -1), (-4, 5), (-5, 4), (-5, -4), (4, 5)]
	assert solve_2_sat(5, ors) is None
	assert solve_2_sat(5, ors[:-1]) == [-1, 2, 3, -4, -5]
