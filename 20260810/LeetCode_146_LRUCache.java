

public class LRUCache {

	private final Node head; // dummy head, next is LRU
	private final Node tail; // dummy tail, prev is MRU

	private final int capacity;
	private final Map<Integer, Node> map = new HashMap<>();

	public LRUCache(int capacity) {

		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be less than 1");
		}

		this.capacity = capacity;

		this.head = new Node();
		this.tail = new Node();

		this.head.prev = null;
		this.head.next = tail;

		this.tail.prev = head;
		this.tail.next = null;
	}

	public void put(int key, int value) {
		Node node = map.get(key);

		if (node != null) {
			node.value = value;
			use(node);
			return;
		}

		if (map.size() == capacity) {
			Node lru = lru();
			remove(lru);
			map.remove(lru.key);
		}

		node = new Node();
		node.key = key;
		node.value = value;
		map.put(key, node);
		insert(node);
	}

	public int get(int key) {
		Node node = map.get(key);
		if (node == null) {
			return -1;
		}
		use(node);
		return node.value;
	}

	// ========

	private static class Node {
		Node prev;
		Node next;
		int key;
		int value;
	}

	private void use(Node node) {
		remove(node);
		insert(node);
	}

	private void remove(Node node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
		node.prev = null;
		node.next = null;
	}

	private void insert(Node node) {
		node.prev = tail.prev;
		node.next = tail;
		tail.prev.next = node;
		tail.prev = node;
	}

	private Node lru() {
		return head.next;
	}
}
