package com.examples.rabbit.consumer.test;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class ConsumerCancelNotification {
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerCancelNotification.class);
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		
		//the default table of client properties that
		//   Map<String, Object> capabilities = new HashMap<String, Object>();
        //capabilities.put("publisher_confirms", true);
        //capabilities.put("exchange_exchange_bindings", true);
       // capabilities.put("basic.nack", true);
       // capabilities.put("consumer_cancel_notify", true);
       // capabilities.put("connection.blocked", true);
       // capabilities.put("authentication_failure_close", true);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		final BlockingQueue<Boolean> result = new ArrayBlockingQueue<Boolean>(1);
		
		String  queue = channel.queueDeclare().getQueue();
		
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleCancel(String consumerTag) throws IOException {
            	LOG.info("handler cancel. The consumer tag is {} ", consumerTag );
                try {
                    result.put(true);
                } catch (InterruptedException e) {
                	LOG.error("throw exception ", e );
                }
            }
        };
        channel.basicConsume(queue, consumer);
       //if delete queue   the  broker will send to the client a basic.cancel 
        channel.queueDelete(queue);
        LOG.info("the resutl from blocking queue is {}", result.take());
	}
}
