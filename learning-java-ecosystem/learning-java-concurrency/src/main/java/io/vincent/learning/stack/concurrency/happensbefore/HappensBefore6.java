package io.vincent.learning.stack.concurrency.happensbefore;

/**
 * 线程 join 规则：
 *  线程 join 后，子线程内所有对共享变量的操作，对于主线程皆【可见】。
 *
 * @author Vincent.
 * @since 1.0, 2019/3/28
 */
public class HappensBefore6 {

    int var = 55;

    public void hb6() throws InterruptedException {
        Thread B = new Thread(() -> {
            // 此处对共享变量 var 修改
            var = 66;
        });
        // 例如：此处对共享变量修改，则这个修改结果对线程 B 可见
        // 主线程启动子线程
        B.start();
        B.join();
        // 子线程所有对共享变量 var 的修改，在主线程调用 B.join() 之后皆可见
        // 此例中，var==66
        System.out.println(var);

    }

    public static void main(String[] args) {
        try {
            new HappensBefore6().hb6();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
