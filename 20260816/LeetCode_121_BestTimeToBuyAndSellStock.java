class Solution {

	public int maxProfit(int[] prices) {
		int ans = 0;
		int min = prices[0];
		for (int price : prices) {
			if (price < min) {
				min = price;
			} else {
				ans = Math.max(ans, price - min);
			}
		}
		return ans;
	}
}