import java.util.Map;
import java.util.HashMap;


public class EventTracker {

	public record Event(String userId, String eventType, long timestamp) {}


	// userId -> eventType -> timestamp BST
	private final Map<String, Map<String, Node>> data = new HashMap<>();


	public int countEvents(String userId, String eventType, long startTime, long endTime) {

		Map<String, Node> byEventType = data.get(userId);

		if (byEventType == null) {
			return 0;
		}

		Node root = byEventType.get(eventType);

		return countLessThanOrEqualTo(root, endTime) - countLessThan(root, startTime);
	}


	private static class Node {

		final long timestamp;

		Node left;
		Node right;

		int count; // occurrences at this timestamp
		int size;  // total occurrences in this subtree

		Node(long timestamp) {

			this.timestamp = timestamp;
			this.count = 1;
			this.size = 1;
		}
	}


	public void add(Event event) {

		Map<String, Node> byEventType = data.computeIfAbsent(event.userId(), k -> new HashMap<>());

		Node root = byEventType.get(event.eventType());
		root = insert(root, event.timestamp());
		byEventType.put(event.eventType(), root);
	}


	private Node insert(Node node, long timestamp) {

		if (node == null) {
			return new Node(timestamp);
		}

		if (timestamp < node.timestamp) {
			node.left = insert(node, timestamp);

		} else if (timestamp > node.timestamp) {
			node.right = insert(node, timestamp);

		} else {
			node.count++;
		}

		node.size = node.count + size(node.left) + size(node.right);

		return node;
	}

	private int size(Node node) {
		return node == null ? 0 : node.size;
	}

	private int countLessThanOrEqualTo(Node node, long timestamp) {

		if (node == null) {
			return 0;
		}

		if (timestamp < node.timestamp) {
			return countLessThanOrEqualTo(node.left, timestamp);
		}

		return size(node.left) + node.count + countLessThanOrEqualTo(node.right, timestamp);
	}

	private int countLessThan(Node node, long timestamp) {

		if (node == null) {
			return 0;
		}

		if (timestamp <= node.timestamp) {
			return countLessThan(node.left, timestamp);
		}

		return size(node.left) + node.count + countLessThan(node.right, timestamp);
	}
}
