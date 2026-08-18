
class Solution {

	public String intToRoman(int num) {
		return intToRoman_v1(num);
	}

	private String intToRoman_v1(int num) {
		String[] ones      = new String[] {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
		String[] tens      = new String[] {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
		String[] hundreds  = new String[] {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
		String[] thousands = new String[] {"", "M", "MM", "MMM"};

		StringBuilder sb = new StringBuilder();
		sb.append( thousands [num        / 1000] );
		sb.append( hundreds  [num % 1000 /  100] );
		sb.append( tens      [num % 100  /   10] );
		sb.append( ones      [num % 10] );

		return sb.toString();
	}

	private String intToRoman_v0(int num) {
		StringBuilder sb = new StringBuilder();		
		num = helper(sb, num, 1000,  "M");
		num = helper(sb, num,  900, "CM");
		num = helper(sb, num,  500,  "D");
		num = helper(sb, num,  400, "CD");
		num = helper(sb, num,  100,  "C");
		num = helper(sb, num,   90, "XC");
		num = helper(sb, num,   50,  "L");
		num = helper(sb, num,   40, "XL");
		num = helper(sb, num,   10,  "X");
		num = helper(sb, num,    9, "IX");
		num = helper(sb, num,    5,  "V");
		num = helper(sb, num,    4, "IV");
		num = helper(sb, num,    1,  "I");
		return sb.toString();
	}

	private int helper(StringBuilder sb, int num, int x, String s) {
		while (num >= x) {
			sb.append(s);
			num -= x;
		}
		return num;		
	}
}
