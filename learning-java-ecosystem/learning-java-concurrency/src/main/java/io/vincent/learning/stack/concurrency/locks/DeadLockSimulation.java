package io.vincent.learning.stack.concurrency.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用 ReentrantLock 模拟死锁。
 *
 * @author Vincent
 * @since 1.0, 2019/4/10
 */
@Slf4j
public class DeadLockSimulation {

    private final Stock stockA = new Stock(50);
    private final Stock stockB = new Stock(100);

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public void addStock(int num) throws InterruptedException {
        log.info("addStock num: {}", num);
        lock1.lock();
        Integer quantity = stockA.getQuantity();
        Thread.sleep(3000);
        stockA.setQuantity(quantity + num);
        log.info("addStockA finished: {}", stockA.getQuantity());

        lock2.lock();
        Integer quantityB = stockB.getQuantity();
        Thread.sleep(3000);
        stockB.setQuantity(quantityB - num);
        log.info("addStockB finished: {}", stockB.getQuantity());
        lock2.unlock();
        lock1.unlock();
    }

    public void subStock(int num) throws InterruptedException {
        log.info("subStock num: {}", num);
        lock2.lock();
        Integer quantity = stockB.getQuantity();
        Thread.sleep(3000);
        stockB.setQuantity(quantity - num);
        log.info("subStockB finished: {}", stockB.getQuantity());

        lock1.lock();
        Integer quantityA = stockA.getQuantity();
        Thread.sleep(3000);
        stockA.setQuantity(quantityA + num);
        log.info("subStockA finished: {}", stockA.getQuantity());
        lock1.unlock();
        lock2.unlock();
    }

    public static void main(String[] args) {

        DeadLockSimulation deadLockSimulation = new DeadLockSimulation();

        // 线程一调用方法 addStock lock1 加锁，试图 lock2 加锁
        new Thread(() -> {
            try {
                deadLockSimulation.addStock(40);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }).start();

        // 线程一调用方法 addStock lock2 加锁，试图 lock1 加锁。
        new Thread(() -> {
            try {
                deadLockSimulation.subStock(50);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }).start();

        log.info("stock A: {}", deadLockSimulation.stockA.getQuantity());
        log.info("stock B: {}", deadLockSimulation.stockB.getQuantity());

    }


}


