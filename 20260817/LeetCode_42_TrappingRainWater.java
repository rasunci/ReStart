import java.util.*;

class Solution {

	public int trap(int[] height) {

		int volume = 0;
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < height.length; i++) {

			while (!stack.isEmpty() && height[stack.peek()] < height[i]) {

				int elevation = height[stack.pop()];
				if (stack.isEmpty()) {
					continue;
				}

				int l = stack.peek();
				int w = i - l - 1;
				int h = Math.min(height[i], height[l]) - elevation;

				volume += w * h;
			}

			stack.push(i);
		}
		return volume;
	}
}
