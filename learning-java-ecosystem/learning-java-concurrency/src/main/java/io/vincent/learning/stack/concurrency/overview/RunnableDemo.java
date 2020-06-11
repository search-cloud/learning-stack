package io.vincent.learning.stack.concurrency.overview;

/**
 * 实现Runnable接口的方式，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
public class RunnableDemo implements Runnable {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    /**
     * 构造一个Runnable实例
     *
     * @param name 线程名称
     */
    public RunnableDemo(String name) {
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
     * 普通start()方法
     */
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            // 真正调用线程类的start()方法，启动线程
            t.start();
        }
    }
}