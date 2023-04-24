package io.vincent.learning.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Send {
    // 队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        BuildChannel buildChannel = new BuildChannel().invoke();
        Connection connection = buildChannel.getConnection();
        Channel channel = buildChannel.getChannel();

        ExecutorService executorService = Executors.newFixedThreadPool(500);

        for (int i = 0; i < 1000; i++) {
            // 发送的消息
            String message = "[" + i + "] hello world!";
            executorService.execute(() -> {
                // 往队列中发出一条消息
                try {
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(" [" + i + "] Sent '" + message + "'");
        }
        executorService.shutdown();

        // 关闭频道和连接
        channel.close();
        connection.close();
    }

    private static class BuildChannel {
        private Connection connection;
        private Channel channel;

        public Connection getConnection() {
            return connection;
        }

        public Channel getChannel() {
            return channel;
        }

        public BuildChannel invoke() throws IOException, TimeoutException {
            // 创建连接连接到RabbitMQ
            ConnectionFactory factory = new ConnectionFactory();
            // 设置MabbitMQ所在主机ip或者主机名
            factory.setHost("127.0.0.1");
            factory.setPort(32771);
            // 创建一个连接
            connection = factory.newConnection();
            // 创建一个频道
            channel = connection.createChannel();
            // 指定一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            return this;
        }
    }
}
