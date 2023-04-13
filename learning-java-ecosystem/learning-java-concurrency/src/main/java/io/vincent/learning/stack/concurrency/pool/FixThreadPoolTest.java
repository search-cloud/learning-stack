package io.vincent.learning.stack.concurrency.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vincent on 2020/12/3.
 *
 * @author Vincent
 * @since 1.0, 2020/12/3
 */
public class FixThreadPoolTest {
	private final ExecutorService executorService = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(50));

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			new Thread(() -> new FixThreadPoolTest().test()).start();
//			sleep();
		}
	}

	private void test() {
		executorService.execute(() -> {
			System.out.println(Thread.currentThread().getName() + "doing something......");
			sleep();
		});
	}

	private static void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
