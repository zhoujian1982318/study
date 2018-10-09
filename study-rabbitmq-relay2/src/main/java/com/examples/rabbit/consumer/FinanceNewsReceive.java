package com.examples.rabbit.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue;

public class FinanceNewsReceive {
	private static final Logger LOG = LoggerFactory.getLogger(FinanceNewsReceive.class);

	private static final String EXCHANGE_NEWS_NAME = "news";

	private static final String QUEUE_NAME = "finance_queue";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		Exchange.DeclareOk declareOk = channel.exchangeDeclare(EXCHANGE_NEWS_NAME, "topic",true);
		LOG.info("declare no-durable topic  exchange 'news' return is {}", declareOk);

		Queue.DeclareOk queDeclare = channel.queueDeclare(QUEUE_NAME, true, // durable
				false, // non-exclusive to the connection
				false, // non-auto-delete
				null);
		LOG.info("declare durable sport_queue the return is {}", queDeclare);

		Queue.BindOk bindSts = channel.queueBind(QUEUE_NAME, EXCHANGE_NEWS_NAME, "finance.#");

		LOG.info("binding queue 'finance_queue' to exchange 'news' using binding key 'finance.#'  the return is {}", bindSts);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {

				String message = new String(body, "UTF-8");
				LOG.info("Received msg is {}. the routing key is {}'", message, envelope.getRoutingKey());
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
