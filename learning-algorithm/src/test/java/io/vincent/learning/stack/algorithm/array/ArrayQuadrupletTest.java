package io.vincent.learning.stack.algorithm.array;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vincent on 10/16/18.
 *
 * @author Vincent
 * @since 1.0, 10/16/18
 */
public class ArrayQuadrupletTest {

	@Test
	public void findArrayQuadruplet0() {
		int[] ints = {};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 12);
		Assert.assertArrayEquals("{}", new int[]{}, quadruplet);
	}

	@Test
	public void findArrayQuadruplet1() {
		int[] ints = {4, 4, 4};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 12);
		Assert.assertArrayEquals("{}", new int[]{}, quadruplet);
	}

	@Test
	public void findArrayQuadruplet2() {
		int[] ints = {4, 4, 4, 2};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 16);
		Assert.assertArrayEquals("{}", new int[]{}, quadruplet);
	}

	@Test
	public void findArrayQuadruplet3() {
		int[] ints = {4, 4, 4, 4};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 16);
		Assert.assertArrayEquals("{4, 4, 4, 4}", ints, quadruplet);
	}

	@Test
	public void findArrayQuadruplet4() {
		int[] ints = {2, 7, 4, 0, 9, 5, 1, 3};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 20);
		Assert.assertArrayEquals("{0, 4, 7, 9}", new int[]{0, 4, 7, 9}, quadruplet);
	}

	@Test
	public void findArrayQuadruplet5() {
		int[] ints = {2, 7, 4, 0, 9, 5, 1, 3};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 120);
		Assert.assertArrayEquals("{}", new int[]{}, quadruplet);
	}

	@Test
	public void findArrayQuadruplet6() {
		int[] ints = {1, 2, 3, 4, 5, 9, 19, 12, 12, 19};
		int[] quadruplet = ArrayQuadruplet.findArrayQuadruplet(ints, 40);
		Assert.assertArrayEquals("{4, 5, 12, 19}", new int[]{4, 5, 12, 19}, quadruplet);
	}
}
