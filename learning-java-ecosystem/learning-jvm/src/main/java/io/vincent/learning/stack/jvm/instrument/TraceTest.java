package io.vincent.learning.stack.jvm.instrument;

/**
 * TraceTest.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class TraceTest {
    public static void main(String[] args) {

        int[] ints = new TraceTest().mixInt(1, 2);

        System.out.println(ints);
        System.out.println(ints[0]);
        System.out.println(ints[1]);

    }

    public int[] mixInt(int i, int j) {
        int[] result = new int[2];

        result[0] = addInt(i, j);
        result[1] = subInt(i, j);

        return result;
    }

    public int addInt(int a, int b) {
        return a + b;
    }

    public int subInt(int x, int y) {
        return x - y;
    }
}
