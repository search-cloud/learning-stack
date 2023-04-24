package io.vincent.learning.mq.rabbitmq.distributed;

import com.rabbitmq.client.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Created by Vincent on 2019/4/21.
 *
 * @author Vincent
 * @since 1.0, 2019/4/21
 */
public class XConsumer {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // Worker1
        work("Worker1");
        // Worker2
        work("Worker2");
    }

    private static void work(String name) throws IOException, TimeoutException {
        final ConnectionFactory factory = getConnectionFactory();
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(name + " Waiting for messages");

        // 每次从队列获取的数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {

                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(name + " Received '" + message + "'");

                try {
//                    throw  new Exception();
                    doWork(message);
                } catch (Exception e) {
                    channel.abort();
                } finally {
                    System.out.println(name + " Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        //消息消费完成确认
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
    }

    @NotNull
    private static ConnectionFactory getConnectionFactory() {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(32771);
        return factory;
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
//            System.out.println(task);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
