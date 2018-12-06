package io.vincent.learning.stack.algorithm.sort;

import java.util.List;

import static io.vincent.learning.stack.algorithm.sort.Printer.*;

/**
 * Created by Vincent on 12/6/18.
 *
 * @author Vincent
 * @since 1.0, 12/6/18
 */
public class InsertionSort {


	static <T extends Comparable<T>> List<T> sort(List<T> list) {
		// zero or one element in list
		if (list == null || list.size() < 2) {
			return list;
		}
		for (int i = 1; i < list.size(); i++) {
			// the current is begin from index of 1
			T current = list.get(i);
			int j = i - 1;
			int origin = j;
			while (j >= 0 && list.get(j).compareTo(current) > 0) {
				// insert into the head of the sorted list
				// or as the first element into an empty sorted list
				// last element of the sorted list
				// middle of the list
				// insert into middle of the sorted list or as the last element
				list.set(j + 1, list.get(j));
				j--;
			}
			list.set(j + 1, current);
			printInsertionSortAnimation(list, current, j, origin);
			System.out.println();
		}

		return list;
	}

}
