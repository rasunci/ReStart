class Solution {

	public int hIndex(int[] citations) {
		sort(citations); // <- reversed here
		// reverse(citations);
		int h = 0;
		for (int i = 0; i < citations.length; i++) {
			if (citations[i] > i) {
				h++;
			}
		}
		return h;
	}

	private void sort(int[] arr) {
		int l = 0;
		int r = arr.length - 1;
		mergesort(arr, l, r);
	}

	private void mergesort(int[] arr, int l, int r) {
		if (l < r) {
			int m = l + ((r - l) / 2);
			mergesort(arr, l, m);
			mergesort(arr, m + 1, r);
			merge(arr, l, m , r);
		}
	}

	private void merge(int[] arr, int l, int m, int r) {

		int[] buffer = new int[r - l + 1];

		int i = 0;
		int a = l;
		int b = m + 1;

		while (a <= m && b <= r) {

			// Descending order
			if (arr[a] >= arr[b]) {
				buffer[i++] = arr[a++];
			} else {
				buffer[i++] = arr[b++];
			}
		}

		while (a <= m) {
			buffer[i++] = arr[a++];
		}

		while (b <= r) {
			buffer[i++] = arr[b++];
		}

		for (i = 0; i < buffer.length; i++) {
			arr[l + i] = buffer[i];
		}
	}

	private void reverse(int[] arr) {
		int l = 0;
		int r = arr.length - 1;
		while (l < r) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
			l++;
			r--;
		}
	}
}