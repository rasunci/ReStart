import java.util.*;

class Solution {

    public List<String> topKFrequent(String[] words, int k) {

		Map<String, Integer> map = new HashMap<>();
		for (String word : words) {
			if (!map.containsKey(word)) {
				map.put(word, 0);
			}
			map.put(word, map.get(word) + 1);
		}

		Queue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
			(a, b) -> {
				int comparison = Integer.compare(a.getValue(), b.getValue());
				if (comparison == 0) {
					comparison = b.getKey().compareTo(a.getKey());
				}
				return comparison;
			}
		);

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			pq.add(entry);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		List<String> ans = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			ans.add(pq.poll().getKey());
		}
		Collections.reverse(ans);
		return ans;
	}
}
