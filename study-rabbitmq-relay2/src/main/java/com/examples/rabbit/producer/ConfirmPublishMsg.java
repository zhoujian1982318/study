package com.examples.rabbit.producer;

import java.io.IOException;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class ConfirmPublishMsg {
	private static final int COUNT = 10000;
	private static final Logger LOG = LoggerFactory.getLogger(ConfirmPublishMsg.class);
	
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String queue = channel.queueDeclare("", true, false, false, null).getQueue();
		StopWatch watch = new StopWatch();
		watch.start(); 
		publishConfirmMsg(channel, queue);
		watch.stop();
	    LOG.info("publish {} msg to queue {}.  waste {} milliseconds ", new Object[] {COUNT,  queue,  watch.getTime()} );
	    channel.close();
        connection.close();
	}

	/**
	 * //channel.confirmSelect();
	 * 如果开启 confirm 模式， 就不能使用 tx 模式
	 * @param channel
	 * @param queue
	 * @throws IOException
	 */
	private static void publishConfirmMsg(Channel channel, String queue) throws IOException {
		//send 10000 msg waste 1400 milliseconds
		 channel.confirmSelect();
		 for (int i = 0; i < COUNT; i++) {
				String msg = "tx-msg-"+i;
		         channel.basicPublish("", queue, false, false, MessageProperties.PERSISTENT_BASIC, msg.getBytes());
		 }
		 try {
			channel.waitForConfirms();
		 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	}
}
