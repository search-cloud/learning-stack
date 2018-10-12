package io.vincent.learning.stack.concurrency.operations;

/**
 * 实现Runnable接口的方式，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
class YieldDemo implements Runnable {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    /**
     * 构造一个Runnable实例
     *
     * @param name 线程名称
     */
    YieldDemo(String name) {
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
        for (int i = 10; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // 只要碰到10的倍数就让给其他线程执行
            if (i % 2 == 0) {
                Thread.yield();
                System.out.println("Thread: " + threadName + ", yield");
            }
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    /**
     * 普通start()方法
     */
    void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            // 真正调用线程类的start()方法，启动线程
            t.start();
        }
    }

    void start(Thread t) {
        this.t = t;
        t.start();
    }

}


