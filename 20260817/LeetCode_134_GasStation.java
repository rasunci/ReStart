class Solution {

	public int canCompleteCircuit(int[] gas, int[] cost) {

		int total = 0;
		int current = 0;
		int start = 0;

		for (int i = 0; i < gas.length; i++) {

			// Can circuit complete
			total   += gas[i] - cost[i];
			current += gas[i] - cost[i];

			if (current < 0) {
				current = 0;
				start = i + 1;
			}
		}

		if (total < 0) {
			return -1;
		}

		return start;
	}
}
