package io.vincent.learning.stack.concurrency.mutex;

/**
 * 简单的模拟银行账户存取款.
 *
 * Created by Vincent on 2/23/19.
 *
 * @author Vincent
 * @since 1.0, 2/23/19
 */
public class BankAccount {
    private Long id;
    private double amount;

    public BankAccount(int amount, Long id) {
        this.amount = amount;
        this.id = id;
    }

    /**
     * 模拟存款
     *
     * @param n 需要存的金额
     */
    public void deposit(double n) {
        amount += n;
    }

    /**
     * 模拟取款
     *
     * @param n 需要取得金额
     * @return 成功与否
     */
    public boolean withdraw(double n) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (amount >= n) {
            amount -= n;
            return true; // 取钱成功
        } else {
            return false; // 余额不足
        }
    }

    /**
     * 查询余额
     *
     * @return 余额
     */
    public double balance() {
        return amount;
    }
}
