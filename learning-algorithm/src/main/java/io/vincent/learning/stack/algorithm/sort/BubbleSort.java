package io.vincent.learning.stack.algorithm.sort;

import java.util.List;

import static io.vincent.learning.stack.algorithm.sort.Printer.printBubbleSortBalancer;
import static io.vincent.learning.stack.algorithm.sort.Printer.printList;

/**
 * Created by Vincent on 12/6/18.
 *
 * @author Vincent
 * @since 1.0, 12/6/18
 */
public class BubbleSort {

	/**
	 * Bubble sort.
	 * <p>
	 * |3|5|1|4|8|6|9|7|2|
	 * |\_______________/|
	 * |1|2|3|4|5|6|7|8|9|
	 *
	 * @param list list
	 * @param <T>  type of
	 */
	static <T extends Comparable<T>> List<T> sort(List<T> list) {
		// zero or one element in list
		if (list == null || list.size() < 2) {
			return list;
		}
		for (int i = 0; i < list.size(); i++) {
			for (int j = list.size() - 1; j > i; j--) {
				printList(list);
				printBubbleSortBalancer(list, i, j);
				if (list.get(i).compareTo(list.get(j)) > 0) swap(list, i, j);
			}
		}
		return list;
	}

	static <T extends Comparable<T>> void swap(List<T> list, int i, int j) {
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

}
