package io.vincent.learning.stack.concurrency.limiter;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * Created by Vincent on 2019/4/6.
 *
 * @author Vincent
 * @since 1.0, 2019/4/6
 */
public class CurrentLimiter<T, R> {

    List<T> list = null;
    Semaphore semaphore = null;

    public CurrentLimiter() {
        this.list = new Vector<>();
        this.semaphore = new Semaphore(1);
    }

    /**
     * 执行限流
     *
     * @param function 需要限流的方法
     */
    public R exec(Function<T, R> function) {
        T t = null;
        try {
            semaphore.acquire();
            t = list.remove(0);
            return function.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            list.add(t);
            semaphore.release();
        }
        return null;
    }


}
