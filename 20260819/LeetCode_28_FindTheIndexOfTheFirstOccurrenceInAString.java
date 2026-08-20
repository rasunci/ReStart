class Solution {

    public int strStr(String haystack, String needle) {
		return kmp(haystack, needle);
	}

	/*
	 * Knuth-Morris-Pratt
	 * LPS = longest prefix/suffix of previous index
	 */

	private int kmp(String string, String pattern) {

		int[] lps = lps(pattern);

		int i = 0;
		int j = 0;

		while (i < string.length()) {

			if (string.charAt(i) == pattern.charAt(j)) {

				i++;
				j++;

				if (j == pattern.length()) {

					// index of pattern match
					int matchIndex = i - j;
					return matchIndex;

					// find more matches:
					// j = lps[j - 1]

				}
			} else if (j != 0) {

				j = lps[j - 1];

			} else {

				i++;
			}
		}
		return -1;
	}

	private int[] lps(String pattern) {

		int[] lps = new int[pattern.length()];

		lps[0]  = 0;
		int len = 0;
		int idx = 1;

		while (idx < pattern.length()) {

			if (pattern.charAt(idx) == pattern.charAt(len)) {

				// pattern match, increment size of lps
				len++;
				lps[idx] = len;
				idx++;

			} else if (len != 0) {

				// mismatch, len = previous lps value
				len = lps[len - 1];

			} else {

				lps[idx] = 0;
				idx++;

			}
		}
		return lps;
	}

	public int strStr_v0(String haystack, String needle) {
		return haystack.indexOf(needle);
	}
}