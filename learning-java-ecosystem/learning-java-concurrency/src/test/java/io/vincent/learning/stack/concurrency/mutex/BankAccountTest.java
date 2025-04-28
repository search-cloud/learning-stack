package io.vincent.learning.stack.concurrency.mutex;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Vincent on 2/23/19.
 *
 * @author Vincent
 * @since 1.0, 2/23/19
 */
public class BankAccountTest {

    private static BankAccount bankAccount = null;

    @BeforeAll
    static void init() {
        bankAccount = new BankAccount(10, 1L);
    }

    @Test
    public void deposit() {
        bankAccount.deposit(1);
    }

    @Test
    public void withdraw() {
        boolean withdraw = bankAccount.withdraw(5);
        assertThat(withdraw).isTrue();
    }

    @Test
    public void concurrentWithdraw() {
        Thread thread1 = new Thread(() -> {
            boolean s = bankAccount.withdraw(5);
            System.out.println(Thread.currentThread().getName() + s);
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            boolean s = bankAccount.withdraw(5);
            System.out.println(Thread.currentThread().getName() + s);
        }, "Thread-2");

//        Thread thread2 = new Thread(() -> {
//            double balance = bankAccount.balance();
//            System.out.println(Thread.currentThread().getName() + balance);
//        }, "Thread-3");

        thread1.start();
        thread2.start();
//        thread2.start();

        double balance = bankAccount.balance();
        System.out.println(Thread.currentThread().getName() + balance);
    }


}
