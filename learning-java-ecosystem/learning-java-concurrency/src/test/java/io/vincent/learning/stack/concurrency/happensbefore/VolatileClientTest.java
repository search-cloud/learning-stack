package io.vincent.learning.stack.concurrency.happensbefore;

import org.junit.Test;

/**
 * Created by Vincent on 2019/3/28.
 *
 * @author Vincent
 * @since 1.0, 2019/3/28
 */
public class VolatileClientTest {

    @Test
    public void volatileExample() {

        VolatileExample ve = new VolatileExample();

        new Thread(ve::read).start();

        new Thread(ve::write).start();

        new Thread(ve::read).start();
    }
}
