package io.vincent.learning.stack.jvm.softleaks;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Vincent on 3/1/19.
 *
 * @author Vincent
 * @since 1.0, 3/1/19
 */
@Slf4j
public class CustomerHarness {

    int a = 1;
    float x = 4;
    Double y = 0.8;

    public static void main(String[] args) {
        CustomerManager cm = new CustomerManager();
        GenerateCustomerTask task = new GenerateCustomerTask(cm);

        for (int user = 0; user < 10; user++) {
            Thread t = new Thread(task);
            t.start();
        }

        // main thread is now acting as the monitoring thread
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cm.howManyCustomers();
            log.info("Available memory: " + Runtime.getRuntime().freeMemory() / 1024 + "k");

        }

        double mix = new CustomerHarness().mix();
        System.out.println(mix);
    }

    public double mix() {
        return x * y + a;
    }

}
