package com.examples.rabbit.consumer.test;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.MessageProperties;

public class DeadLetterExchangeTest {
	
	private static final String DLX = "dead.letter.exchange";
	private static final String DLX_ARG = "x-dead-letter-exchange";
	private static final String DLX_RK_ARG = "x-dead-letter-routing-key";
	private static final String TEST_QUEUE_NAME = "test.queue.dead.letter";
	private static final String DLQ = "queue.dlq";
	private static final String DLQ2 = "queue.dlq2";
	private static final int MSG_COUNT = 10;
	private static final int TTL = 1000;
	private static final Logger LOG = LoggerFactory.getLogger(DeadLetterExchangeTest.class);
	private static final int COUNT = 10;

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		Map<String, Object> arguments = new HashMap<String, Object>();
		arguments.put("x-message-ttl", 5000);
		arguments.put("x-dead-letter-exchange", DLX);
		
		channel.exchangeDeclare(DLX, "direct", true);
		channel.queueDeclare(DLQ, true, false, false, null);
        channel.queueDeclare(TEST_QUEUE_NAME, true, false, false, arguments);
        channel.queueBind(TEST_QUEUE_NAME, "amq.direct", "test");
        channel.queueBind(DLQ, DLX, "test");
        
        channel.basicPublish("amq.direct", "test", MessageProperties.MINIMAL_BASIC, "deal-letter-test".getBytes());
        
        Thread.sleep(7000);
        GetResponse getResponse = channel.basicGet(DLQ, true);
        LOG.info("the response  from  dead-lettered is {} ", getResponse);
        LOG.info("the test  message is {}", new String(getResponse.getBody()));
	}
	
	
}
