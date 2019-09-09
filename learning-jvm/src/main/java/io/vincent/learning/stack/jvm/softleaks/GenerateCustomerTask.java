package io.vincent.learning.stack.jvm.softleaks;

import io.vincent.learning.stack.jvm.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vincent on 3/1/19.
 *
 * @author Vincent
 * @since 1.0, 3/1/19
 */
@Slf4j
public class GenerateCustomerTask implements Runnable {

    private CustomerManager cm;
    private int totalCustomersGenerated = 0;
    private AtomicInteger count = new AtomicInteger(1);

    public GenerateCustomerTask(CustomerManager cm) {
        this.cm = cm;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Simulate user adding a customer through a web page
            String name = new UUID(1l, 10l).toString();
            Customer c = new Customer(name, count.getAndIncrement());
            cm.addCustomer(c);
            totalCustomersGenerated++;
            cm.getNextCustomer();
//            log.info("TotalCustomersGenerated: {}", totalCustomersGenerated);
        }
    }

}

