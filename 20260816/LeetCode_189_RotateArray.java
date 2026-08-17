class Solution {

	public void rotate(int[] nums, int k) {

		/*

		Input: nums = [1,2,3,4,5,6,7], k = 3
		Output: [5,6,7,1,2,3,4]

		Intuition
		int n = nums.length;
		k = k % n;

		[1,2,3,4,5,6,7], k = 3
		[5,6,7,1,2,3,4]
            [3]

		[1,2,3,4,5,6,7], if k = 10, 10 % 7 = 3
		    [10]

		reverse all
        [7,6,5,4,3,2,1]

        reverse to k
        [5,6,7] [4,3,2,1]

        reverse from k
        [5,6,7] [1,2,3,4] <-

		*/

		int n = nums.length;
		k = k % n;
		reverse(nums, 0, n - 1);
		reverse(nums, 0, k - 1);
		reverse(nums, k, n - 1);
	}

	public void reverse(int[] arr, int left, int right) {
		while (left < right) {
			int tmp = arr[left];
			arr[left] = arr[right];
			arr[right] = tmp;
			left++;
			right--;
		}
	}
}
