import java.util.*;

class RandomizedSet {

	private final List<Integer> list = new ArrayList<>();
	Map<Integer, Integer> map = new HashMap<>();
	Random random = new Random();

	public RandomizedSet() {
	}

	public boolean insert(int val) {
		if (map.containsKey(val)) {
			return false;
		}
		map.put(val, list.size());
		list.add(val);
		return true;
	}

	public boolean remove(int val) {
		if (!map.containsKey(val)) {
			return false;
		}
		int last = list.get(list.size() - 1);
		int index = map.get(val);

		map.put(last, index);
		list.set(index, last);

		map.remove(val);
		list.remove(list.size() - 1);
		return true;
	}

	public int getRandom() {
		return list.get(random.nextInt(list.size()));
	}
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */