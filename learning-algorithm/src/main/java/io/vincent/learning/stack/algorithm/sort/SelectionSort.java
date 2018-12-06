package io.vincent.learning.stack.algorithm.sort;

import java.util.List;

import static io.vincent.learning.stack.algorithm.sort.Printer.printList;

import static io.vincent.learning.stack.algorithm.sort.BubbleSort.swap;

/**
 * Created by Vincent on 12/6/18.
 *
 * @author Vincent
 * @since 1.0, 12/6/18
 */
public class SelectionSort {

	static <T extends Comparable<T>> List<T> sort(List<T> list) {
		// zero or one element in list
		if (list == null || list.size() < 2) {
			return list;
		}
		int size = list.size();
		for (int i = 0; i < size - 1; i++) {
			T min = list.get(i);
			int minIndex = i;
			for (int j = i + 1; j < size; j++) {
				if (min.compareTo(list.get(j)) > 0) {
					min = list.get(j);
					minIndex = j;
				}
			}
			swap(list, i, minIndex);
			printList(list);
		}

		return list;
	}
}
