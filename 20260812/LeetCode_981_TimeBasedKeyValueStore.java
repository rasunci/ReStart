import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;


class TimeMap {

	private final Map<String, TreeMap<Integer, String>> hashMap;

    public TimeMap() {
        hashMap = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        TreeMap treeMap = hashMap.computeIfAbsent(key, k -> new TreeMap<>());
		treeMap.put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        if (hashMap.containsKey(key)) {
			TreeMap<Integer, String> treeMap = hashMap.get(key);
			Integer floorKey = treeMap.floorKey(timestamp);
			if (floorKey != null) {
				return treeMap.get(floorKey);
			}
		}
		return "";
    }
}

public class LeetCode_981_TimeBasedKeyValueStore {

	public static void main(String[] args) {
	}
}
