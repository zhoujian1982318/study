package com.examples.rabbit.consumer.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class RequeueTest {

	private static final String Q = "RequeueOnClose";

	private final Connection connection;
	
	private final Random random = new Random(); ;

	private Channel channel;

	public RequeueTest(Connection conn) throws IOException {
		this.connection = conn;
	}

	private static final Logger LOG = LoggerFactory.getLogger(RequeueTest.class);

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		Connection connection = factory.newConnection();
		RequeueTest requeue = new RequeueTest(connection);
		//requeue.requeueOnCloseTest(3);
		//requeue.requeueOnRejectTest();
		requeue.requeueOnNackTest();
		connection.close();
	}

	private void requeueOnNackTest() throws IOException {
		openChannel();
		injectMessage();
		injectMessage();
		GetResponse nackMsg =  nackMessage();
		LOG.info("nack message  {}", new String(nackMsg.getBody()));
		GetResponse rMsg = getMessage();
		LOG.info("requeue message  {}", new String(rMsg.getBody()));
		deleteQueue();
		closeChannel();
		
	}

	private GetResponse nackMessage() throws IOException {
		GetResponse rsp1 =  channel.basicGet(Q, false);
		int msgCount = rsp1.getMessageCount();
		LOG.info("the  message count {} on the rsp1 ", msgCount);
		GetResponse rsp2 =  channel.basicGet(Q, false);
		msgCount = rsp2.getMessageCount();
		LOG.info("the  message count {} on the rsp2 ", msgCount);
		//如果 multiple 为 true, 将requeue all unacknowledged msg. 到 devlery tag 为止。
		//如果 multiple 为 fasle, 将requeue only single  unacknowledged msg. 即devlert tag 的msg
		channel.basicNack(rsp2.getEnvelope().getDeliveryTag(),true, true);
		return rsp2;
	}

	private void requeueOnRejectTest() throws IOException {
		openChannel();
		injectMessage();
		injectMessage();
		GetResponse rejectMsg =  rejectMessage();
		LOG.info("reject message  {}", new String(rejectMsg.getBody()));
		GetResponse rMsg = getMessage();
		LOG.info("requeue message  {}", new String(rMsg.getBody()));
		deleteQueue();
		closeChannel();
	}
	private void deleteQueue() throws IOException {
		channel.queueDelete(Q);
		
	}

	private GetResponse rejectMessage() throws IOException {
		GetResponse response =  channel.basicGet(Q, false);
		int msgCount = response.getMessageCount();
		LOG.info("the  message count {} on the q ", msgCount);
		channel.basicReject(response.getEnvelope().getDeliveryTag(),true);
		return response;
	}
	private void openChannel() throws IOException {
		channel = connection.createChannel();
	}

	private void closeChannel() throws IOException {
		if (channel != null) {
			channel.abort();
			channel = null;
		}
	}

	private void injectMessage() throws IOException {
		channel.queueDeclare(Q, false, false, true, null);
		int randInt = random.nextInt(50);
		String msg = "random msg "+ randInt;
		channel.basicPublish("", Q, null, msg.getBytes());
		LOG.info("publish msg {} to the  q", msg);
	}

	private GetResponse getMessage() throws IOException {
		return channel.basicGet(Q, false);
	}

	public void requeueOnCloseTest(int count) throws IOException {
		for (int repeat = 0; repeat < count; repeat++) {
			openChannel();
			injectMessage();
			GetResponse r1 = getMessage();
			LOG.info("get message from q {}", new String(r1.getBody()));
			closeChannel();
			openChannel();
			GetResponse r2 = getMessage();
			LOG.info("get requeue message from q {}", new String(r2.getBody()));
			closeChannel();
		}
	}

}
