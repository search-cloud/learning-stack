package io.vincent.learning.stack.concurrency.happensbefore;

/**
 * Created by Vincent on 2019/3/28.
 *
 * @author Vincent
 * @since 1.0, 2019/3/28
 */
public class HappensBefore5 {

    int var = 66;

    public void hb5() {

        Thread B = new Thread(() -> {
            // 主线程调用 B.start() 之前
            // 所有对共享变量的修改，此处皆可见
            // 此例中，var==77
            System.out.println(var);
        });
        // 此处对共享变量 var 修改
        var = 77;
        // 主线程启动子线程
        B.start();
    }

}
