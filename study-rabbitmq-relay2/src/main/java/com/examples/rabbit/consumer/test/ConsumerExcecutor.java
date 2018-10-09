package com.examples.rabbit.consumer.test;

import com.examples.rabbit.consumer.EntNewsReceive;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConsumerExcecutor {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerExcecutor.class);

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.20.139");
        ArrayBlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(5,5,60L, TimeUnit.SECONDS, taskQueue);
        Connection connection = factory.newConnection(executor);
        final Channel channel = connection.createChannel();
        String queue ="test-memory";
        AMQP.Queue.DeclareOk queDeclare = channel.queueDeclare(queue, true, false, false, null);
        LOG.info("declare durable sport_queue the return is {}", queDeclare);

        //不设置，默认值是多少？
        //channel.basicQos(1);
//
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                LOG.info("cosmumer 1 is consuming msg...");
                String message = new String(body, "UTF-8");
                LOG.info("Received msg is {}. the routing key is {}'", message, envelope.getRoutingKey());
                LOG.info("the current thread is {}", Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                LOG.info("the thread pool info ：，the pool size is {}, the size of queue is {},  the task count is  {} ",
//                        new Object[]{executor.getPoolSize(), executor.getQueue().size(), executor.getTaskCount()});
//                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(queue, true, consumer);


        LOG.info("the declare consumer  2");
        Channel channel2 = connection.createChannel();
        Consumer consumer2 = new DefaultConsumer(channel2) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                LOG.info("cosmumer 2 is consuming msg...");
                String message = new String(body, "UTF-8");
                LOG.info("Received msg is {}. the routing key is {}'", message, envelope.getRoutingKey());
                LOG.info("the current thread is {}", Thread.currentThread().getName());
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                LOG.info("the thread pool info ：，the pool size is {}, the size of queue is {},  the task count is  {} ",
//                        new Object[]{executor.getPoolSize(), executor.getQueue().size(), executor.getTaskCount()});
            }
        };

        channel2.basicConsume(queue, true, consumer2);
    }
}
