---
title: Java Concurrency Perspective Inter-thread Communication（三）Java并发透视之线程间通信-生产者消费者
date: 2017-08-17 17:30:31
categories: [Java, Concurrency]
tags: [Java, Concurrency]
---

- [tags: [Java, Concurrency]](#tags-java-concurrency)
- [使用 wait() & notify()/notifyAll() 模拟生产者消费者](#%E4%BD%BF%E7%94%A8-wait--notifynotifyall-%E6%A8%A1%E6%8B%9F%E7%94%9F%E4%BA%A7%E8%80%85%E6%B6%88%E8%B4%B9%E8%80%85)
  - [简述与单个 Producer 和 Consumer 线程](#%E7%AE%80%E8%BF%B0%E4%B8%8E%E5%8D%95%E4%B8%AA-producer-%E5%92%8C-consumer-%E7%BA%BF%E7%A8%8B)
  - [多个 Producer 线程和多个 Consumer 线程如何如何实现？](#%E5%A4%9A%E4%B8%AA-producer-%E7%BA%BF%E7%A8%8B%E5%92%8C%E5%A4%9A%E4%B8%AA-consumer-%E7%BA%BF%E7%A8%8B%E5%A6%82%E4%BD%95%08%E5%A6%82%E4%BD%95%E5%AE%9E%E7%8E%B0)

## 使用 wait() & notify()/notifyAll() 模拟生产者消费者

### 简述与单个 Producer 和 Consumer 线程
以上讲的几个方法，在 Thread 类里面都能找到，然而，wait(), notify()/notifyAll()，却不在 Thread 类里，而是在 Object 类里面。简单说明这三个方法：

1. public void wait() 促使当前线程等待，直到另一个线程调用 notif() 来唤醒，才有可能重新执行。
2. public void notify() 唤醒正在此对象监视器上等待的一个线程。
3. public void notifyAll() 唤醒在同一对象上调用 wait() 的所有线程。

如果你了解进程间通信，那么你将很容易理解跨线程通信。当开发一个两个或多个线程交换某些信息的应用程序时，Interthread通信很重要。我们以一个简单的例子，来看看这几个方法在线程间是怎么工作的。
主要的代码，如下：

```java
/**
 * Thread's communication Message Queue.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class MessageQueue {

    private val queue = LinkedList<Message>()
    private val pCounter = AtomicInteger()
    private val cCounter = AtomicInteger()
    private val capacity = 1

    @Synchronized
    internal fun produce(message: Message) {

        // Producer 线程等待，当消息队列满的时候
        while (queue.size == capacity) {
//            println("Message Queue is full: $capacity, waiting for consume!")
            wait()
        }

        // 添加消息到消息队列中
        queue.add(message)
        println("Producer produced-${pCounter.getAndIncrement()} $message")

        // 唤醒一个 Consumer 线程可以消费了
        notify()

        // 睡眠1秒，模拟现实工作
        Thread.sleep(1000)
    }

    @Synchronized
    internal fun consume(): Message? {

        // Consumer 线程等待 Producer 线程生产，当消息队列为空时
        while (queue.isEmpty()) {
//            println("Message Queue is empty, waiting for produce!")
            wait()
        }

        // 在消息队列消费一个消息
        val message = queue.poll()
        println("Consumer consumed-${cCounter.getAndIncrement()} $message")

        // 唤醒一个 Producer 线程生产消息了
        notify()

        // 睡眠1秒，模拟现实工作
        Thread.sleep(1000)

        return message
    }

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun Any.wait() = (this as java.lang.Object).wait()

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun Any.notify() = (this as java.lang.Object).notify()
}
```

模拟客户端，启动一个 Producer 线程，一个 Consumer 线程：

```java
/**
 * Test.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
object SingleProducerConsumerTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val m = MessageQueue()
        SingleProducer(m)
        SingleConsumer(m)
    }
}
```

```java
/**
 * Producer monitor.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class SingleProducer(private val messageQueue: MessageQueue) : Runnable {

    init {
        Thread(this, "Producer").start()
    }

    override fun run() {
        for (i in 0 until 10) {
            messageQueue.produce(Message(Thread.currentThread().id + i, "Message-Head-$i", "Message-Body-$i"))
        }
    }

}

/**
 * Consumer monitor.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class SingleConsumer(private val messageQueue: MessageQueue) : Runnable {

    init {
        Thread(this, "Consumer").start()
    }

    override fun run() {
        for (i in 0 until 10) {
            messageQueue.consume()
        }
    }

}
```

只能在 Synchronized 上下文中调用所有三种方法。如果，不在 Synchronized 上下文中使用，则会报 IllegalMonitorStateException :
![IllegalMonitorStateException](../../../assets/img/java/java-concurrency-03-01.png)

### 多个 Producer 线程和多个 Consumer 线程如何如何实现？

笔者毕竟能力有限，难免有疏漏，如果，大家发现文章有何错误，请不吝赐教。谢谢！