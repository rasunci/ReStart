import java.util.*;

class Solution {

	public int romanToInt(String s) {
		return romanToInt_v1(s);
	}

	public int romanToInt_v1(String s) {
		int ans = 0;
		int prev = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			int value = value(s.charAt(i));
			if (prev > value) {
				ans -= value;
			} else {
				ans += value;
			}
			prev = value;
		}
		return ans;
	}

	private int value(char c) {
		switch (c) {
			case 'M': return 1000;
			case 'D': return  500;
			case 'C': return  100;
			case 'L': return   50;
			case 'X': return   10;
			case 'V': return    5;
			default : return    1;
		}
	}

	public int romanToInt_v0(String s) {
		Map<String, Integer> map = new HashMap<>();
		map.put("M"  , 1000);
		map.put("CM" ,  900);
		map.put("D"  ,  500);
		map.put("CD" ,  400);
		map.put("C"  ,  100);
		map.put("XC" ,   90);
		map.put("L"  ,   50);
		map.put("XL" ,   40);
		map.put("X"  ,   10);
		map.put("IX" ,    9);
		map.put("V"  ,    5);
		map.put("IV" ,    4);
		map.put("I"  ,    1);
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
				ans += map.get(s.substring(i, i + 2));
				i++;
			} else {
				ans += map.get(s.substring(i, i + 1));
			}
		}
		return ans;
	}
}