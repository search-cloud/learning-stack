package io.vincent.learning.stack.concurrency.overview;

/**
 * 继承Thread类，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
public class ThreadDemo extends Thread {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    public ThreadDemo(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    /**
     * 重写run()方法
     * 从100倒数到1。
     */
    @Override
    public void run() {
        System.out.println("Running " + threadName);
        for (int i = 100; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    /**
     * 重写父类的start()方法
     * 谨慎重写父类的start()方法
     */
    @Override
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            // 真正调用父类的start()方法，启动线程
            t.start();
        }
    }
}
