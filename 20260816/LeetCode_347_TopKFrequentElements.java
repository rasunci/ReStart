import java.util.*;

class Solution {

    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
			if (!map.containsKey(num)) {
				map.put(num, 0);
			}
			map.put(num, map.get(num) + 1);
        }

        Queue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(
			(a, b) -> {
				return Integer.compare(a.getValue(), b.getValue());
			}
        );

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.add(entry);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = pq.poll().getKey();
        }

        return ans;
    }
}