class Solution {

    public int lengthOfLastWord(String s) {

		String[] ss = s.split("\\s");
		String last = ss[ss.length - 1];
		return last.length();

	}
}