package io.vincent.learning.stack.concurrency.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 开源应用:
 *  1. Quartz
 *  2. Spring 定时任务
 *
 * Created by Vincent on 2/22/19.
 *
 * @author Vincent
 * @since 1.0, 2/22/19
 */
public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        // 每隔1秒打印一次
        timer.schedule(new TimerTask() {
            int i = 1;
            @Override
            public void run() {
                System.out.println(i++);
            }
        }, 0,1000);
    }
}
