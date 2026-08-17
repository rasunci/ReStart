class Solution {

	public int jump(int[] nums) {

		int jumps = 0;
		int range = 0;
		int farthest = 0;

		for (int i = 0; i < nums.length - 1; i++) {

			farthest = Math.max(farthest, nums[i] + i);

			if (i == range) {
				jumps++;
				range = farthest;
			}
		}

		return jumps;
	}
}
