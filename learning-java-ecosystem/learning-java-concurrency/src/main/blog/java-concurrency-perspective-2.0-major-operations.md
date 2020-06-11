---
title: Java Concurrency Perspective Major Operations (二) </br> Java 并发透视之主要操作
date: 2017-08-17 14:0:01
categories: [Java, Concurrency]
tags: [Java, Concurrency]
---

- [Java 并发透视之主要操作（二）](#java-%E5%B9%B6%E5%8F%91%E9%80%8F%E8%A7%86%E4%B9%8B%E4%B8%BB%E8%A6%81%E6%93%8D%E4%BD%9C%E4%BA%8C)
  - [Java 多线程的主要操作概览](#java-%E5%A4%9A%E7%BA%BF%E7%A8%8B%E7%9A%84%E4%B8%BB%E8%A6%81%E6%93%8D%E4%BD%9C%E6%A6%82%E8%A7%88)
  - [sleep()](#sleep)
  - [setPriority(int) & getPriority()](#setpriorityint--getpriority)
  - [yield()](#yield)
  - [join()](#join)
  - [wait() & notify()/notifyAll()](#wait--notifynotifyall)

# Java 并发透视之主要操作（二）

## Java 多线程的主要操作概览

|         方法          | 简介                                                 |
|:--------------------:|:----------------------------------------------------|
|   **sleep(long)**    | 该方法使当前线程睡眠一定时间，单位为毫秒（ms）            |
|  **getPriority()**   | 该方法可以获取线程的优先级                             |
| **setPriority(int)** | 该方法设置线程的优先级，但是并不能保证按这个优先级优先运行 |
|     **yield()**      | 该方法让出CUP资源，让其他线程先执行                     |
|      **join()**      | 该方法合并一个线程到当前线程                            |
|      **wait()**      | 当前线程等待其他线程调用notify()后才会继续运行           |
|     **notify()**     | 该方法唤醒一个等待中的线程                             |
|   **notifyAll()**    | 该方法所有正在等待的线程                               |

## sleep()
该方法使当前线程睡眠一定时间，单位为毫秒（ms）
还是上一节的例子，但是我们在run()方法的循环体中添加了 Thread.sleep(50); 语句。
这样输出的结果就是，Thread-1：倒计时一次，然后，Thread-2：倒计时一次。
```
Creating Thread-1
Starting Thread-1
Creating Thread-2
Starting Thread-2
Running Thread-1
Thread: Thread-1, 10
Running Thread-2
Thread: Thread-2, 10
Thread: Thread-2, 9
Thread: Thread-1, 9
Thread: Thread-2, 8
Thread: Thread-1, 8
Thread: Thread-2, 7
Thread: Thread-1, 7
Thread: Thread-1, 6
Thread: Thread-2, 6
Thread: Thread-2, 5
Thread: Thread-1, 5
Thread: Thread-2, 4
Thread: Thread-1, 4
Thread: Thread-2, 3
Thread: Thread-1, 3
Thread: Thread-2, 2
Thread: Thread-1, 2
Thread: Thread-2, 1
Thread: Thread-1, 1
Thread Thread-2 exiting.
Thread Thread-1 exiting.
```
{% note danger %}
因为，循环每执行一次，线程就睡眠50毫秒，所以，使得倒计时比较均匀。
Thread-1倒计时一次，然后，Thread-2倒计时一次。
{% endnote %}
demo code：

```java
package io.asion.concurrent;

import org.junit.Test;

/**
 * 实现Runnable接口的方式，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
class RunnableDemo implements Runnable {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    /**
     * 构造一个Runnable实例
     *
     * @param name 线程名称
     */
    RunnableDemo(String name) {
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
        try {
            for (int i = 10; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
}

public class TestRunnableSleep {
    // 启动两个线程测试
    @Test
    public void testRunnableSleep() {
        RunnableDemo r1 = new RunnableDemo("Thread-1");
        r1.start();

        RunnableDemo r2 = new RunnableDemo("Thread-2");
        r2.start();
    }
}
```

## setPriority(int) & getPriority()
{% note danger %}
**getPriority()**方法可以获取该线程的优先级
__setPriority(int)__方法设置线程的优先级，但是JVM并不能保证按这个优先级优先运行
{% endnote %}

```java
package io.asion.concurrent;

import org.junit.Test;

/**
 * 实现Runnable接口的方式，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
class PriorityDemo implements Runnable {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    /**
     * 构造一个Runnable实例
     *
     * @param name 线程名称
     */
    PriorityDemo(String name) {
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
        try {
            for (int i = 100; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        this.t.start();
    }

}

public class TestPriority {
    // 启动两个线程测试
    @Test
    public void testPriority() {
        PriorityDemo r1 = new PriorityDemo("Thread-1");
        Thread thread = new Thread(r1, "Thread-1");
        thread.setPriority(Thread.MIN_PRIORITY);
        r1.start(thread);

        PriorityDemo r2 = new PriorityDemo("Thread-2");
        Thread thread1 = new Thread(r1, "Thread-2");
        System.out.println(thread1.getPriority());
        r2.start();
    }
}
```

## yield()

Thread.yield()
给其他线程让出CUP资源，让其他线程先执行。

```
package io.asion.concurrent;

import org.junit.Test;

/**
 * @author Asion.
 * @since 2017/4/5.
 */
public class TestYield {
    // 启动两个线程测试
    @Test
    public void testYield() {
        YieldDemo r1 = new YieldDemo("Thread-1");
        r1.start();

        YieldDemo r2 = new YieldDemo("Thread-2");
        r2.start();
    }
}

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
            // 只要碰到2的倍数就让给其他线程执行
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
```
{% note danger %}
例子中，只要碰到2的倍数就让给其他线程执行
{% endnote %}
输出的结果:
```
Creating Thread-1
Starting Thread-1
Creating Thread-2
Starting Thread-2
Running Thread-1
Thread: Thread-1, 10
Running Thread-2
Thread: Thread-2, 10
Thread: Thread-1, yield
Thread: Thread-1, 9
Thread: Thread-1, 8
Thread: Thread-2, yield
Thread: Thread-2, 9
Thread: Thread-2, 8
Thread: Thread-1, yield
Thread: Thread-1, 7
Thread: Thread-1, 6
Thread: Thread-2, yield
Thread: Thread-2, 7
Thread: Thread-2, 6
Thread: Thread-1, yield
Thread: Thread-1, 5
Thread: Thread-1, 4
Thread: Thread-2, yield
Thread: Thread-2, 5
Thread: Thread-2, 4
Thread: Thread-1, yield
Thread: Thread-1, 3
Thread: Thread-1, 2
Thread: Thread-2, yield
Thread: Thread-2, 3
Thread: Thread-2, 2
Thread: Thread-1, yield
Thread: Thread-1, 1
Thread Thread-1 exiting.
Thread: Thread-2, yield
Thread: Thread-2, 1
Thread Thread-2 exiting.

Process finished with exit code 0

```

## join()

join 例子:
```
package io.asion.concurrent;

import org.junit.Test;

/**
 * @author Asion.
 * @since 2017/4/9.
 */
public class TestJoin {
    // 启动两个线程测试
    @Test
    public void testJion() {
        JoinDemo r1 = new JoinDemo("Thread-1");
        Thread thread = new Thread(r1, "Thread-1");
        r1.start(thread);

        JoinDemo r2 = new JoinDemo("Thread-2");
        r2.start();

        r1.join();
        r2.join();

        for (int i = 10; i > 0; i--) {
            System.out.println("Thread: main" + Thread.currentThread().getId() + ", " + i);
        }

        System.out.println("Thread main" + Thread.currentThread().getId() + " finish.");
    }
}

/**
 * 实现Runnable接口的方式，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
class JoinDemo implements Runnable {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    /**
     * 构造一个Runnable实例
     *
     * @param name 线程名称
     */
    JoinDemo(String name) {
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

    void join() {
        try {
            this.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
{% note danger %}
例子中，r1、r2两个线程都调用了join(),所以main线程必须等待r1、r2线程执行完成，才可以往下执行。
{% endnote %}
输出结果:
```
Creating Thread-1
Creating Thread-2
Starting Thread-2
Running Thread-1
Thread: Thread-1, 10
Thread: Thread-1, 9
Thread: Thread-1, 8
Thread: Thread-1, 7
Thread: Thread-1, 6
Thread: Thread-1, 5
Thread: Thread-1, 4
Thread: Thread-1, 3
Thread: Thread-1, 2
Thread: Thread-1, 1
Thread Thread-1 exiting.
Running Thread-2
Thread: Thread-2, 10
Thread: Thread-2, 9
Thread: Thread-2, 8
Thread: Thread-2, 7
Thread: Thread-2, 6
Thread: Thread-2, 5
Thread: Thread-2, 4
Thread: Thread-2, 3
Thread: Thread-2, 2
Thread: Thread-2, 1
Thread Thread-2 exiting.
Thread: main1, 10
Thread: main1, 9
Thread: main1, 8
Thread: main1, 7
Thread: main1, 6
Thread: main1, 5
Thread: main1, 4
Thread: main1, 3
Thread: main1, 2
Thread: main1, 1
Thread main1 finish.
```

## wait() & notify()/notifyAll()

详见下一篇文章 [Java并发透视之线程间通信](/posts/java/concurrency/java-concurrency-perspective-003-inter-threads-communication.html)

笔者毕竟能力有限，难免有疏漏，如果，大家发现文章有何错误，请不吝赐教。谢谢！
