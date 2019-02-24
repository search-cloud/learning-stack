package io.vincent.learning.stack.algorithm.sort;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.vincent.learning.stack.algorithm.sort.Printer.printList;

/**
 * Created by Vincent on 12/6/18.
 *
 * @author Vincent
 * @since 1.0, 12/6/18
 */
public class BubbleSortTest {

	@Test
	public void testBubbleSort() {
		List<Integer> list = Arrays.asList(3, 5, 1, 4, 8, 6, 9, 7, 2);

		System.out.println("size: " + list.size());
		System.out.println("before sort: ");
		printList(list);

		System.out.println("begin: ");
		List<Integer> sorts = BubbleSort.sort(list);

		System.out.println("after sort: ");
		printList(sorts);
	}

}