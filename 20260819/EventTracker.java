import java.util.Map;
import java.util.HashMap;

class EventTracker {

	public record Event(long timestamp, String userId, String eventType) {}

	private static class Node {

		long timestamp;
		int count; // events exactly in this timestamp
		int size;  // total events in this subtree

		Node left;
		Node right;

		Node(long timestamp) {
			this.timestamp = timestamp;
			this.count = 1;
			this.size = 1;
		}
	}

	private final Map<String, Map<String, Node>> data = new HashMap<>();

	public void addEvent(Event event) {

		Map<String, Node> byType = data.computeIfAbsent(event.userId, k -> new HashMap<>());

		Node root = byType.get(event.eventType());
		root = insert(root, event.timestamp());
		byType.put(event.eventType(), root);
	}

	private Node insert(Node node, long timestamp) {

		if (node == null) {
			return new Node(timestamp);
		}

		if (timestamp < node.timestamp) {
			node.left = insert(node.left, timestamp);
		} else if (timestamp > node.timestamp) {
			node.right = insert(node.right, timestamp);
		} else {
			node.count++;
		}

		updateSize(node);
		return node;
	}

	private void updateSize(Node node) {
		node.size = node.count + size(node.left) + size(node.right);
	}

	private int size(Node node) {
		return node == null ? 0 : node.size;
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

	private int countLessThanOrEqual(Node node, long timestamp) {

		if (node == null) {
			return 0;
		}

		if (timestamp < node.timestamp) {
			return countLessThanOrEqual(node.left, timestamp);
		}

		return size(node.left) + node.count + countLessThanOrEqual(node.right, timestamp);
	}

	public int countEvents(String userId, String eventType, long startTime, long endTime) {

		Map<String, Node> byType = data.get(userId);

		if (byType == null) {
			return 0;
		}

		Node root = byType.get(eventType);

		if (root == null) {
			return 0;
		}

		return countLessThanOrEqual(root, endTime) - countLessThan(root, startTime);
	}

	public static void main (String[] args) {
		EventTracker eventTracker = new EventTracker();
		eventTracker.addEvent(new Event(100, "alice", "login"));
		eventTracker.addEvent(new Event(101, "bob",   "login"));
		eventTracker.addEvent(new Event(105, "alice", "purchase"));
		eventTracker.addEvent(new Event(110, "alice", "logout"));
		System.out.println(eventTracker.countEvents("alice", "login", 100, 110));
	}
}
