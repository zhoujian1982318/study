package com.examples.rabbit.producer;

import java.io.IOException;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Guarantee publish message using transactions on  AMQP 0-9-1
 * @author Administrator
 *
 */
public class TxPublishMsg {
	private static final int COUNT = 10000;
	private static final Logger LOG = LoggerFactory.getLogger(TxPublishMsg.class);
	
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String queue = channel.queueDeclare("", true, false, false, null).getQueue();
		StopWatch watch = new StopWatch();
		watch.start(); 
		publishNouaranteeMsg(channel, queue);
		watch.stop();
	    LOG.info("publish {} msg to queue {}.  waste {} milliseconds ", new Object[] {COUNT,  queue,  watch.getTime()} );
	    channel.close();
        connection.close();
	}

	public static void publishTxMsg(Channel channel, String queue) throws IOException {
		//using tx mode
        channel.txSelect();
        //send 10000 msg waste 1474 milliseconds
		for (int i = 0; i < COUNT; i++) {
			String msg = "tx-msg-"+i;
	         channel.basicPublish("", queue, false, false, MessageProperties.PERSISTENT_BASIC, msg.getBytes());
	    }
		channel.txCommit();
	}
	
	/**
	 * The persistence guarantees aren't strong，  但是已经足够对普通的应用。msg可能需要几百毫秒，才会写disk
	 * @param channel
	 * @param queue
	 * @throws IOException
	 */
	public static void publishNouaranteeMsg(Channel channel, String queue) throws IOException {
        //send 10000 msg waste 430 milliseconds
		for (int i = 0; i < COUNT; i++) {
			String msg = "tx-msg-"+i;
	         channel.basicPublish("", queue, false, false, MessageProperties.PERSISTENT_BASIC, msg.getBytes());
	    }
	}
}
