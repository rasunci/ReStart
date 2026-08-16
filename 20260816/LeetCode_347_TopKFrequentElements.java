import java.util.*;

class Solution {

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
			map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Queue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(
			(a, b) -> {
	            return Integer.compare(a.getValue(), b.getValue());
			}
        );
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.add(entry);
            while (pq.size() > k) {
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