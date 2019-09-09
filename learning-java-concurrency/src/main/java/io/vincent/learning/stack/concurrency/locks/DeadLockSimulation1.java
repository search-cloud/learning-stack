package io.vincent.learning.stack.concurrency.locks;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用 synchronized 模拟死锁。
 *
 * @author Vincent
 * @since 1.0, 2019/4/10
 */
@Slf4j
public class DeadLockSimulation1 {
    private final Stock stockA = new Stock(50);
    private final Stock stockB = new Stock(100);

    public void addStock(int num) throws InterruptedException {
        log.info("addStock num: {}", num);
        synchronized (stockA) {
            Integer quantity = stockA.getQuantity();
            Thread.sleep(3000);
            stockA.setQuantity(quantity + num);
            log.info("addStockA finished: {}", stockA.getQuantity());

            synchronized (stockB) {
                Integer quantityB = stockB.getQuantity();
                Thread.sleep(3000);
                stockB.setQuantity(quantityB - num);
                log.info("addStockB finished: {}", stockB.getQuantity());
            }
        }
    }

    public void subStock(int num) throws InterruptedException {
        log.info("subStock num: {}", num);
        synchronized (stockB) {
            Integer quantity = stockB.getQuantity();
            Thread.sleep(3000);
            stockB.setQuantity(quantity - num);
            log.info("subStockB finished: {}", stockB.getQuantity());

            synchronized (stockA) {
                Integer quantityA = stockA.getQuantity();
                Thread.sleep(3000);
                stockA.setQuantity(quantityA + num);
                log.info("subStockA finished: {}", stockA.getQuantity());
            }
        }
    }

    public static void main(String[] args) {

        DeadLockSimulation1 deadLockSim = new DeadLockSimulation1();

        // 线程一锁定了对象stockA，等待锁定stockB
        new Thread(() -> {
            try {
                deadLockSim.addStock(40);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }).start();

        // 线程二锁定了对象stockB， 等待锁定stockA
        new Thread(() -> {
            try {
                deadLockSim.subStock(50);
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }).start();

        log.info("stock A: {}", deadLockSim.stockA.getQuantity());
        log.info("stock B: {}", deadLockSim.stockB.getQuantity());

    }


}

