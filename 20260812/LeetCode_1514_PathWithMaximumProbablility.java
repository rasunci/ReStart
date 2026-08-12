import java.util.*;


class Solution {

	static class Edge {
		final int target;
		final double probability;

		Edge(int target, double probability) {
			this.target = target;
			this.probability = probability;
		}
	}

	static class Node implements Comparable<Node> {
		final int id;
		final double probability;

		Node(int id, double probability) {
			this.id = id;
			this.probability = probability;
		}

		@Override
		public int compareTo(Node other) {
			// Max heap, we want higher probability
			return Double.compare(other.probability, probability);
		}
	}

	static double dijkstra(List<List<Edge>> graph, int n, int start, int end) {
		double[] probabilities = new double[n]; // probabilities defaults to 0.0d

		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 1.0d)); // start probability = 1.0
		probabilities[start] = 1.0d;

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			int curr = node.id;
			double currProbability = node.probability;

			// Skip stale, we want higher probability
			if (probabilities[curr] > currProbability) {
				continue;
			}

			if (curr == end) {
				return currProbability;
			}

			// Expore neighbors
			for(Edge edge : graph.get(curr)) {
				int next = edge.target;
				double nextProbability = currProbability * edge.probability;

				// Relax, we want higher probability
				if (nextProbability > probabilities[next]) {
					probabilities[next] = nextProbability;
					pq.add(new Node(next, nextProbability));
				}
			}
		}
		return probabilities[end];
	}

    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
		List<List<Edge>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int i = 0; i < edges.length; i++) {
			int[] edge = edges[i];
			double probability = succProb[i];
			graph.get(edge[0]).add(new Edge(edge[1], probability)); // Undirected graph
			graph.get(edge[1]).add(new Edge(edge[0], probability));
		}
		return dijkstra(graph, n, start_node, end_node);
	}
}
