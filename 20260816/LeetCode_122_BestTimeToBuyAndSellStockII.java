class Solution {

	public int maxProfit(int[] prices) {
        int profit = 0;
		for (int i = 1; i < prices.length; i++) {
			int earn = prices[i] - prices[i - 1];
			if (earn > 0) {
				profit += earn;
			}
		}
		return profit;
	}
}
