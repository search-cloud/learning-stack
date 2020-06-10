package io.vincent.learning.stack.concurrency.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable 代返回值的 Runnable
 *
 * Future 异步任务
 *
 *
 *
 * @author Vincent
 * @since 1.0, 2/22/19
 */
public class CallableAndFutureDemo implements Callable<Integer> {

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableAndFutureDemo());

        try {

            Thread t = new Thread(futureTask);
            t.start();

            //
            System.out.println("可以先做点别的任务...");
            // 触发异步任务
            Integer result = futureTask.get();

            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Integer call() throws Exception {

        System.out.println("异步计算中...");
        // 模拟计算时间
        Thread.sleep(3000);

        return 1;
    }
}
