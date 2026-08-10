

public class LeetCode_875_KokoEatingBananas {

	public int minK_BruteForce1 (int[] piles, int h) {
		if (piles.length > h) {
			throw new IllegalArgumentException("h cannot be greater than piles.length");
		}
		int min = findMax(piles);
		int tmp = min;
		while (isPossible1(tmp, piles, h)) {
			min = tmp;
			tmp--;
		}
		return min;
	}

	public int minK_BruteForce2 (int[] piles, int h) {
		if (piles.length > h) {
			throw new IllegalArgumentException("h cannot be greater than piles.length");
		}
		int min = findMax(piles);
		int tmp = min;
		while (isPossible2(tmp, piles, h)) {
			min = tmp;
			tmp--;
		}
		return min;
	}

    public int minK_BinarySearch (int[] piles, int h) {
		if (piles.length > h) {
			throw new IllegalArgumentException("h cannot be greater than piles.length");
		}
		int left = 0;
		int right = findMax(piles);
        while (left < right) {
			int mid = left + ((right - left) / 2);
            if (isPossible2(mid, piles, h)) {
				right = mid;
			} else {
				left = mid + 1;
			}
        }
		return left;
    }

	public boolean isPossible1(int min, int[] piles, int h) {
		int elapsed = 0;
		for (int i = 0; i < piles.length; i++) {
			int curr = piles[i];
			while (curr > min) {
				curr = curr - min;
				elapsed++;
			}
			elapsed++;
		}
		return elapsed <= h;
	}

	public boolean isPossible2(int min, int[] piles, int h) {
		int elapsed = 0;
		for (int i = 0; i < piles.length; i++) {
			elapsed += (int) Math.ceil((double) piles[i] / min);
		}
		return elapsed <= h;
	}

	public int findMax(int[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException("array cannot be null nor empty");
		}
		int max = Integer.MIN_VALUE;
		for (int x : array) {
			if (x > max) {
				max = x;
			}
		}
		return max;
	}

	public int test1() {
		LeetCode_875_KokoEatingBananas test = new LeetCode_875_KokoEatingBananas();
		int[] piles = new int[] { 3, 6, 7, 11 };
		int h = 8;

		int a = minK_BruteForce1(piles, h);
		int b = minK_BruteForce2(piles, h);
		int c = minK_BinarySearch(piles, h);
		if (a == b && b == c) {
			return a;
		}
		throw new IllegalStateException(String.format("Failed test %d %d %d", a, b, c));
	}

	public int test2() {
		int[] piles = new int[] { 30, 11, 23, 4, 20 };
		int h = 5;

		int a = minK_BruteForce1(piles, h);
		int b = minK_BruteForce2(piles, h);
		int c = minK_BinarySearch(piles, h);
		if (a == b && b == c) {
			return a;
		}
		throw new IllegalStateException(String.format("Failed test %d %d %d", a, b, c));
	}

	public int test3() {
		int[] piles = new int[] { 30, 11, 23, 4, 20 };
		int h = 6;

		int a = minK_BruteForce1(piles, h);
		int b = minK_BruteForce2(piles, h);
		int c = minK_BinarySearch(piles, h);
		if (a == b && b == c) {
			return a;
		}
		throw new IllegalStateException(String.format("Failed test %d %d %d", a, b, c));
	}

	public int test3b() {
		int[] piles = new int[] { 30, 11, 23, 4, 20 };
		int h = 6;
		return minK_BruteForce2(piles, h);
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		LeetCode_875_KokoEatingBananas test = new LeetCode_875_KokoEatingBananas();
		System.out.printf("test 1: %d \n", test.test1());
		System.out.printf("test 2: %d \n", test.test2());
		System.out.printf("test 3: %d \n", test.test3());
	}
}
