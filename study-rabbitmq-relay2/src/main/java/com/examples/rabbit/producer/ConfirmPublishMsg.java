package com.examples.rabbit.producer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmPublishMsg {
	private static final int COUNT = 1024;
	private static final Logger LOG = LoggerFactory.getLogger(ConfirmPublishMsg.class);
	
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		String queue = channel.queueDeclare("", true, false, false, null).getQueue();
		String queue ="test-memory";
		channel.queueDeclare(queue, true, false, false, null);
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
		//byte[]  test = new byte [1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		for(int i=0; i< 1024; i++){
			out.write(0X31);
		}
		byte[] test = out.toByteArray();
		//send 10000 msg waste 1400 milliseconds
		channel.addConfirmListener(new ConfirmListener() {
			public void handleAck(long seqNo, boolean multiple) {
				//System.out.println("message ack......");
			}

			public void handleNack(long seqNo, boolean multiple) {
				//System.out.println("message nack......");
			}
		});
		channel.confirmSelect();
		for (int i = 0; i < COUNT*200; i++) {
				//String msg = "tx-msg-"+i;
			channel.basicPublish("", queue, false, false, MessageProperties.PERSISTENT_BASIC, test);
			System.out.println("send successfully....");
			 try {
				 TimeUnit.MILLISECONDS.sleep(1);
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }

		 }
//		 try {
//			channel.waitForConfirms();
//		 } catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		 }
	}
}
