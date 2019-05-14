package graph

import extensions.PriorityPair
import java.util.*


class Digraph<V>(private val defaultEdgeValue: Double = 1.0) {

	private val edges = HashMap<V, HashMap<V, Double>>()
	private val nodes = LinkedHashSet<V>()

	fun getNodes(): ArrayList<V> = nodes.toCollection(arrayListOf<V>())

	fun addNode(node: V) {
		nodes.add(node)
	}

	//overrides old value if present
	fun addEdge(a: V, b: V, weight: Double = defaultEdgeValue) {
		nodes.add(a)
		nodes.add(b)
		edges.putIfAbsent(a, HashMap<V, Double>())
		edges[a]?.put(b, weight)
	}

	fun addBidirectionalEdge(a: V, b: V, weight: Double = defaultEdgeValue) {
		addEdge(a, b, weight)
		addEdge(b, a, weight)
	}

	fun getNeighbors(node: V): Set<V> {
		return edges[node]?.keys ?: emptySet<V>()
	}

	fun getWeight(a: V, b: V): Double {
		if (!hasEdge(a, b))
			throw IllegalArgumentException("edge does not exist")
		return edges[a]?.get(b)!!
	}

	fun getWeightOr(a: V, b: V, valueIfAbsent: Double): Double {
		if (!hasEdge(a, b))
			return valueIfAbsent
		return edges[a]?.get(b)!!
	}

	fun hasEdge(a: V, b: V): Boolean {
		return null != edges[a]?.get(b)
	}

	fun dijkstra(startNode: V, goalFunction: (V) -> Boolean): Pair<Double, ArrayList<V>>? {
		val parents = HashMap<V, V>()
		val visited = HashSet<V>()
		val pq = PriorityQueue<PriorityPair<V>>()
		pq.add(PriorityPair(0.0, startNode))
		while (!pq.isEmpty()) {
			var (currentPathLength, currentNode) = pq.poll()
			if (goalFunction(currentNode)) {
				val path = ArrayList<V>()
				var midNode: V? = currentNode
				while (midNode != null) {
					path.add(midNode)
					midNode = parents[midNode]
				}
				return Pair(currentPathLength, path.reversed().toCollection(arrayListOf<V>()))
			}
			visited.add(currentNode)
			getNeighbors(currentNode).filter { it !in visited }.forEach { neighbor ->
				val w = getWeight(currentNode, neighbor)
				pq.add(PriorityPair(w + currentPathLength, neighbor))
				parents.put(neighbor, currentNode)
			}
		}
		return null
	}

	fun concatMultiply(you: Digraph<V>): Digraph<V> {
		val res = Digraph<V>()
		for (nodeA in this.getNodes()) {
			for (nodeB in this.getNeighbors(nodeA)) {
				val weightAB = this.getWeight(nodeA, nodeB)
				for (nodeC in you.getNeighbors(nodeB)) {
					val weightBC = you.getWeight(nodeB, nodeC)
					var weightAC = weightAB * weightBC
					if (res.hasEdge(nodeA, nodeC))
						weightAC += res.getWeight(nodeA, nodeC)
					res.addEdge(nodeA, nodeC, weightAC)
				}
			}
		}
		return res
	}


}