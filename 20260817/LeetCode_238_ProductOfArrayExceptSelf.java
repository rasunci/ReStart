class Solution {

	public int[] productExceptSelf(int[] nums) {
		return productExceptSelf_v1(nums);
	}

	public int[] productExceptSelf_v0(int[] nums) {

		int n = nums.length;

		int[] prefix = new int[n];
		int product = 1;
		for (int i = 0; i < n; i++) {
			prefix[i] = product;
			product *= nums[i];
		}

		int[] postfix = new int[n];
		product = 1;
		for (int i = n - 1; i >= 0; i--) {
			postfix[i] = product;
			product *= nums[i];
		}

		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = prefix[i] * postfix[i];
		}

		return result;
	}

	public int[] productExceptSelf_v1(int[] nums) {

		int n = nums.length;

		int[] result = new int[n];

		int prefix = 1;
		for (int i = 0; i < n; i++) {
			result[i] = prefix;
			prefix *= nums[i];
		}

		int postfix = 1;
		for (int i = n - 1; i >= 0; i--) {
			result[i] *= postfix;
			postfix *= nums[i];
		}

		return result;
	}
}
