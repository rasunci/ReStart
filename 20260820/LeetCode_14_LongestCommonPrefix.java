

class Solution {

	public String longestCommonPrefix(String[] strs) {
		return longestCommonPrefix_v0(strs);
	}

	public String longestCommonPrefix_v0(String[] strs) {

		if (strs.length == 0) {
			return strs[0];
		}

		int i = 0;
		for (; i < strs[0].length(); i++) {
			for (int j = 1; j < strs.length; j++) {
				if (i == strs[j].length() || strs[0].charAt(i) != strs[j].charAt(i)) {
					return strs[0].substring(0, i);
				}
			}
		}

		return strs[0].substring(0, i);
	}
}
