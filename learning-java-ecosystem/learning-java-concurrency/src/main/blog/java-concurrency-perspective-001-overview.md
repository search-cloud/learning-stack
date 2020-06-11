---
title: Java Concurrency Perspective Overview (一) </br> Java 并发透视之初窥探（一）
date: 2017-03-24 12:07:01
categories: [java, concurrency]
tags: [java, concurrency]
---

Java 并发透视之初窥探（一）
====

## 概述
Java是一种支持多线程编程的语言，意味着，我们可以使用Java开发多线程程序。一个多线程程序包含两个或多个可以同时运行的部件，每个部件可以同时处理不同的任务，特别是当计算机有多个CPU时，可以有效地利用可用资源。
多任务是指多个进程共享共同的处理资源，如CPU。多线程将多任务的概念扩展到可以将单个应用程序中的特定操作细分为单个线程的应用程序。每个线程可以并行运行。OS不仅在不同的应用程序之间划分处理时间，而且在应用程序中的每个线程之间划分处理时间。
多线程使您能够以同一程序同时进行多个活动的方式进行写入。


## 线程的生命周期
线程生命周期经历了几个阶段：线程诞生，启动，运行，阻塞，死亡。下图简要显示了一个线程的完整生命周期。

----
                    +-------------+   start()  \=-------------=/
     new Thread()-->|     New     |------------>\  Runnable   /<---------+
                    +------+------+              \-----+-----/           |
                           |                           |                 |
                           |                           |run()            |
                           v           End of          v                 |
                    +------+------+   execution /------+-------\         |
              End<--\    Dead     /<------------|   Running    |         |
                    -------+-------             \------+-------/         | Release Block
                           ^                           |                 |
                           |                           |sleep(), wait()  |    
                           |      =-------------=      |                 |
                           +------|   Blocked   |<-----+                 |
                                  =------+------=                        |
                                         |                               |
                                         |                               |
                                         +-------------------------------+
                              Life cycle of a Thread
                           -----------------------------        
----

以下是生命周期的各个阶段简述：
- **新的（New）** - 新线程在新的状态下开始其生命周期。直到程序启动线程为止，它保持在这种状态。它也被称为**新生线程**。
- **可运行的（Runnable）** - 新诞生的线程启动后，该线程可以运行。该状态的线程被认为正在执行其任务。但是，可能还未正式运行，可能在等待资源，如：CPU。
- **运行中的（Running）** - 新诞生的线程启动后，如果，所有的准备条件已经满足，该线程会从准备运行状态进入运行状态，执行任务。
- **等待（Waiting）** - 有时线程会转换到等待状态，而线程等待另一个线程执行任务。只有当另一个线程发信号通知等待线程才能继续执行时，线程才转回到可运行状态。
- **定时等待（Timed Waiting）** - 可运行的线程可以在指定的时间间隔内进入定时等待状态。当该时间间隔到期或发生等待的事件时，此状态的线程将转换回可运行状态。
- **终止（Dead）** - 可执行线程在完成任务或以其他方式终止时进入终止状态。

## 线程的优先级
每一个线程都有一个优先级，可以帮助操作系统确定安排线程的顺序。

Java线程优先级在MIN_PRIORITY（常量为1）和MAX_PRIORITY（常量为10）之间的范围内。默认情况下，每个线程都被赋予优先级NORM_PRIORITY（常量为5）。

具有较高优先级的线程对于一个程序来说更重要，应该在低优先级线程之前分配处理器时间。然而，线程优先级不能保证线程执行的顺序，并且依赖于平台。

## 创建自己的第一个线程
{% note success %}
### Java主要可以通过两种方式创建多线程的程序：
{% endnote %}
#### 1.通过实现Runnable接口创建一个线程（**推荐使用**）

```java
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
        for (int i = 100; i > 0; i--) {
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
}

public class TestRunnable {
    // 启动两个线程测试
    public static void main(String args[]) {
        RunnableDemo r1 = new RunnableDemo("Thread-1");
        r1.start();

        RunnableDemo r2 = new RunnableDemo("Thread-2");
        r2.start();
    }
}
```

#### 2.通过继承Thread类创建一个线程

```java
/**
 * 继承Thread类，创建线程
 *
 * @author Asion
 * @since 2017/03/24
 */
class ThreadDemo extends Thread {
    // 持有Thread的一个引用
    private Thread t;
    // 线程名称
    private String threadName;

    ThreadDemo(String name) {
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

public class TestThread {
    // 启动两个线程测试
    public static void main(String args[]) {
        ThreadDemo t1 = new ThreadDemo("Thread-1");
        t1.start();

        ThreadDemo t2 = new ThreadDemo("Thread-2");
        t2.start();
    }
}
```

### 我们来简单分析一下多线程的程序
- 首先，我们的RunnableDemo实现的Runnable接口，那必须重写run()方法，run()就是线程running运行的方法，是线程体。
- 当new一个Thread实例，传入我们的RunnableDemo实例，这时线程生命周期处在新建（new）阶段；
- 然后，当调用Thread实例的start()方法时，说明线程已经准备就绪，转到了就绪（Runnable）状态；
- 如果此时，分配到了CPU，调用了run()方法开始执行，就转到运行（Running）状态；
- 当run()方法运行结束，线程就结束；或者run()方法中异常导致线程结束退出。

通过这一小节我们，我们了解java线程的创建和启动。下一节我们来看看java多线程的主要操作。
    