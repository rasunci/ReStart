import java.util.Map;
import java.util.HashMap;


// 2461. Maximum Sum of Distinct Subarrays With Length K

class Solution {

	public long maximumSubarraySum(int[] nums, int k) {

		long max = 0L;
		long ans = 0L;

		Map<Integer, Integer> freq = new HashMap<>();
		for (int i = 0; i < k; i++) {
			ans += nums[i];
			freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);
		}

		if (freq.size() == k) {
			max = ans;
		}

		for (int i = k; i < nums.length; i++) {
			ans -= nums[i - k];
			if (freq.get(nums[i - k]) == 1) {
				freq.remove(nums[i - k]);
			} else {
				freq.put(nums[i - k], freq.get(nums[i - k]) - 1);
			}

			ans += nums[i];
			freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);

			if (freq.size() == k) {
				max = Math.max(max, ans);
			}
		}

		return max;
	}
}
