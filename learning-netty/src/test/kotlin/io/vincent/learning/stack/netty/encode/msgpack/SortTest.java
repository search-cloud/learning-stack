package io.vincent.learning.stack.netty.encode.msgpack;

import org.junit.Test;

/**
 * Created by Vincent on 8/31/18.
 *
 * @author Vincent
 * @since 1.0, 8/31/18
 */
public class SortTest {


	@Test
	public void testSort1() {
		int[] a = {3, 6, 7, 4, 2, 8, 1, 5, 9};

		System.out.println();
		System.out.println("Type1:");
		System.out.println("before sort: ");
		print(a);

		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] > a[j]) {
					int temp = a[j];
					a[j] = a[i];
					a[i] = temp;
				}
			}
		}

		System.out.println();
		System.out.println("after sort: ");
		print(a);
	}
	@Test
	public void testSort2() {
		int[] a = {7, 6, 8, 4, 9, 3, 1, 5, 2};

		System.out.println("\n");
		System.out.println("Type2: ");
		System.out.println("before sort: ");
		print(a);

		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				if (a[i] < a[j]) {
					int temp = a[j];
					a[j] = a[i];
					a[i] = temp;
				}
			}
		}

		System.out.println();
		System.out.println("after sort: ");
		print(a);
	}

	private void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]);
			if (i != a.length - 1) {
				System.out.print(", ");
			}
		}
	}
}
