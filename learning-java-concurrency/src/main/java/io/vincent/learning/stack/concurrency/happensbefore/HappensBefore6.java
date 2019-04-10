package io.vincent.learning.stack.concurrency.happensbefore;

/**
 * Created by Vincent on 2019/3/28.
 *
 * @author Vincent
 * @since 1.0, 2019/3/28
 */
public class HappensBefore6 {

    int var = 55;

    public void hb6() throws InterruptedException {
        Thread B = new Thread(() -> {
            // 此处对共享变量 var 修改
            var = 66;
        });
        // 例如此处对共享变量修改，
        // 则这个修改结果对线程 B 可见
        // 主线程启动子线程
        B.start();
        B.join();
        // 子线程所有对共享变量的修改
        // 在主线程调用 B.join() 之后皆可见
        // 此例中，var==66

    }
}
