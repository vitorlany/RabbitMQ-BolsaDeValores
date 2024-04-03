package app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Main {
    private final static String QUEUE_NAME = "bolsaDeValores";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("qqvipbxv");
        factory.setVirtualHost("qqvipbxv");
        factory.setPassword("hw8GYRPOfSi2kE1JGgHNsyd7A2oEKySx");
        factory.setPort(5672);
        factory.setUri("amqps://qqvipbxv:hw8GYRPOfSi2kE1JGgHNsyd7A2oEKySx@gull.rmq.cloudamqp.com/qqvipbxv");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}