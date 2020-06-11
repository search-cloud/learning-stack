package io.vincent.learning.stack.concurrency;

/**
 * Created by Vincent on 2/23/19.
 *
 * @author Vincent
 * @since 1.0, 2/23/19
 */
public class ConcurrencyTest {
    private static final long count = 100_000_000;

    public static void main(String[] args) {
        concurrency();
        serial();
    }

    private static void concurrency() {
        long start = System.currentTimeMillis();
        final int[] a = new int[1];
        Thread thread = new Thread(() -> {
            int j = 0;
            for (long i = 0; i < count; i++) {
                j += 5;
            }
            a[0] = j;
        });

        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("concurrency: " + time + "ms, b=" + b + ", a=" + a[0]);
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial: " + time + "ms, b=" + b + ", a="+a);
    }
}
