
class Solution {

	public String convert(String s, int numRows) {

		if (numRows == 1) {
			return s;
		}

		StringBuilder[] sbs = new StringBuilder[numRows];
		for (int i = 0; i < numRows; i++) {
			sbs[i] = new StringBuilder();
		}

		int counter = 0;
		boolean down = true;
		for (int i = 0; i < s.length(); i++) {
			if (down) {
				sbs[counter++].append(s.charAt(i));
				if (counter == numRows - 1) {
					down = false;
				}
			} else {
				sbs[counter--].append(s.charAt(i));
				if (counter == 0) {
					down = true;
				}
			}
		}

		StringBuilder ans = new StringBuilder();
		for (StringBuilder sb : sbs) {
			ans.append(sb.toString());
		}

		return ans.toString();
	}
}
