class Solution {

    public boolean canJump(int[] nums) {
		int reach = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (i > reach) {
				return false;
			}
			reach = Math.max(reach, nums[i] + i);
			if (reach >= nums.length - 1) {
				return true;
			}
		}
		return true;
	}
}
