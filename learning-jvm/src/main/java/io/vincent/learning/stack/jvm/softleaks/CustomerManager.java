package io.vincent.learning.stack.jvm.softleaks;

import io.vincent.learning.stack.jvm.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 3/1/19.
 *
 * @author Vincent
 * @since 1.0, 3/1/19
 */
@Slf4j
public class CustomerManager {

    private List<Customer> customers = new ArrayList<Customer>();
    private int nextId = 0;

    public void addCustomer(Customer customer) {
        synchronized (this) {
            customer.setId((long) nextId);
            nextId++;
        }
        customers.add(customer);
    }

    public Customer getNextCustomer() {
        // should do:
        // FIXME customers.remove(0);
        // for test.
        return customers.get(0);
//        synchronized (this) {
//            if (customers.size() != 0) {
//                return customers.remove(0);
//            }
//            return null;
//        }
    }

    public void howManyCustomers() {
        int size = customers.size();
        log.info("size: {}", size);
    }

    public void displayCustomers() {
        synchronized (customers) {
            for (Customer c : customers) {
                log.info(c.toString());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error("display customer exception: ", e);
                }
            }
        }
    }


}
