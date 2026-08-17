class Solution {

	public int removeDuplicates(int[] nums) {

		// 1 <= nums.length <= 3 * 104
		int index = 1;

		for (int i = 1; i < nums.length; i++) {
			if (nums[i - 1] == nums[i]) {
				continue;
			}
			nums[index++] = nums[i];
		}

		return index;
	}
}