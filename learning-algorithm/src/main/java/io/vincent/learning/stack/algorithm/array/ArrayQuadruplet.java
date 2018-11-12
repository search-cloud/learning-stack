package io.vincent.learning.stack.algorithm.array;

import java.util.Arrays;

/**
 * Created by Vincent on 10/15/18.
 *
 * @author Vincent
 * @since 1.0, 10/15/18
 */
public class ArrayQuadruplet {

	static int[] findArrayQuadruplet(int[] arr, int s) {
		// your code goes here
		int[] outArr = {};
		int n = arr.length;
		if (n < 4) return outArr;

		Arrays.sort(arr);

		for (int i = 0; i < n - 3; i++) {
			for (int j = n - 1; j > i + 2; j--) {
				int r = s - (arr[i] + arr[j]);

				int low = i + 1;
				int high = j - 1;

				while (low < high) {
					if (arr[low] + arr[high] < r) {
						low++;
					} else if (arr[low] + arr[high] > r) {
						high--;
					} else {
						outArr = new int[4];
						outArr[0] = arr[i];
						outArr[1] = arr[low];
						outArr[2] = arr[high];
						outArr[3] = arr[j];
						return outArr;
					}
				}
			}
		}

		return outArr;
	}


	public static void main(String[] args) {
		int[] ints = {2, 7, 4, 0, 9, 5, 1, 3};

		long start = System.currentTimeMillis();
		int[] arr = findArrayQuadruplet(ints, 20);
		long end = System.currentTimeMillis();

		System.out.println(end - start);

		for (int i : arr) {
			System.out.print(i);
			System.out.print(" ");
		}
	}


}
