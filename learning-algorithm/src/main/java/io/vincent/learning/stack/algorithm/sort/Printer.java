package io.vincent.learning.stack.algorithm.sort;

import java.util.List;

/**
 * Created by Vincent on 12/6/18.
 *
 * @author Vincent
 * @since 1.0, 12/6/18
 */
public class Printer {

	private static final char space = ' ';
	private static final char underLine = '_';
	private static final char verticalLine = '|';
	private static final char backSlash = '\\';
	private static final char slash = '/';

	/**
	 * Print List.
	 *
	 * @param list list.
	 * @param <T> type of the element.
	 */
	static <T extends Comparable<T>> void printList(List<T> list) {
		String listString = getListString(list);
		System.out.println(listString);
	}

	private static <T extends Comparable<T>> String getListString(List<T> list) {
		StringBuilder sb = new StringBuilder();
		for (T t : list) {
			sb.append(verticalLine);
			sb.append(t);
		}
		sb.append(verticalLine);
		return sb.toString();
	}

	static <T extends Comparable<T>> void printBubbleSortBalancer(List<T> list, int i, int j) {
		int total = (list.size() * 2) + 1 - 4;
		int iNumbers = (i * 2) + 1 - 4;
		int jNumbers = (j * 2) + 1 - 4;
		int underLineNumbers = jNumbers + 1 - iNumbers - 2;
		int spaceNumbers = total - jNumbers - 2;

		printSeparator(verticalLine, 1);

		printSeparator(space, i * 2);
		printSeparator(backSlash, 1);

		printSeparator(underLine, underLineNumbers);
		printSeparator(slash, 1);

		printSeparator(space, spaceNumbers);
		printSeparator(verticalLine, 1);
		System.out.println();
	}

	static <T extends Comparable<T>> void printInsertionSortAnimation(List<T> list, T current, int j, int bakJ) {
		String listString = getListString(list);
		System.out.println(listString);

		int jNumbers = (j * 2) + 1;
		int bjNumbers = (bakJ * 2) + 1;

		printSeparator(space, jNumbers + 2);
		printSeparator(current, 1);
		printSeparator('<', bjNumbers - jNumbers);
	}

	private static <D> void printSeparator(D separator, int numbers) {
		for (int i = 0; i < numbers; i++) {
			System.out.print(separator);
		}
	}
}
