package io.vincent.learning.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * io.vincent.learning.lock.LockClient.
 *
 * @author Vincent.Lu.
 * @since 2023/4/17
 */
public class LockClient {

    RedissonClient redisson = Redisson.create();


    public static void main(String[] args) throws InterruptedException {
        RLock orderLock = new LockClient().redisson.getLock("OrderLock");

        orderLock.lock();

        Thread.sleep(2000);

        orderLock.unlock();
    }
}
