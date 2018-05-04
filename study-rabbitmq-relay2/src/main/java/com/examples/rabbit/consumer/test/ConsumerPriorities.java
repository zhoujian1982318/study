package com.examples.rabbit.consumer.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;

public class ConsumerPriorities {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerPriorities.class);
	private static final int COUNT = 10;
	
	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		QueueMessageConsumer highConsumer = new QueueMessageConsumer(channel);
	    QueueMessageConsumer medConsumer = new QueueMessageConsumer(channel);
	    QueueMessageConsumer lowConsumer = new QueueMessageConsumer(channel);
	    
		String queue = "temp-priorities";
		Map<String,Object> 	arguments = new HashMap<String,Object>();
		channel.queueDeclare(queue, false, false, true, arguments);
		
		LOG.info("declare the queue. the queue is  {}", queue);
		
		
		//channel.basicQos(5);
		
		String high = channel.basicConsume(queue, true, prioritiesMap(8), highConsumer);
		
		LOG.info("start high consume. the high consumer tag is {}", high);
	       
		String med = channel.basicConsume(queue, true, prioritiesMap(5), medConsumer);
		
		LOG.info("start med consume. the med consumer tag is {}", med);
		//which do not specify a value have priority 0.  负数可以的
		String low = channel.basicConsume(queue, true, prioritiesMap(-1), lowConsumer);
		
		LOG.info("start low consume. the low consumer tag is {}", low);
		
		channel.addReturnListener(new ReturnListener() {
			
			@Override
			public void handleReturn(int replyCode,
		            String replyText,
		            String exchange,
		            String routingKey,
		            AMQP.BasicProperties properties,
		            byte[] body)
					throws IOException {
			
				LOG.info("the relay text is {}， exchange is  {}, the routingKey is {}", new Object[] {replyText, exchange, routingKey} );
				
			}
		});
		
		publish(channel, queue, COUNT, "high");
		
		//if 如果没有 cancal high cosumer, autoAck = false 还象还和 autoAck 和 basicQos 有关  其他的cosumer 是接收不到消息的，除非它阻塞 channel.basicCancel(high);
		channel.basicCancel(high);
		highConsumer.cancelLatch.await();
		LOG.info("High priority consumer should have been cancelled ");
		
		publish(channel, queue, COUNT, "med");
		
		
		//channel.basicCancel(med);
		//medConsumer.cancelLatch.await();
	    //LOG.info("Medium priority consumer should have been cancelled ");
		publish(channel, queue, COUNT, "low");
		
		
		
	}
	
	private static void publish(Channel channel, String queue, int count, String msg) throws IOException {
		
		for (int i = 0; i < count; i++) {
            channel.basicPublish("", queue, true, MessageProperties.MINIMAL_BASIC, msg.getBytes("UTF-8"));
        }
		
	}

	private static Map<String, Object> prioritiesMap(Integer priority) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-priority", priority);
		return args;
	}

	private static class QueueMessageConsumer extends DefaultConsumer {

	        BlockingQueue<String> messages = new LinkedBlockingQueue<String>();

	        CountDownLatch cancelLatch = new CountDownLatch(1);
	        
	        private final Channel channel; 
	        public QueueMessageConsumer(Channel channel) {
	            super(channel);
	            this.channel = channel;
	        }

	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	        	String msg = new String (body, "UTF-8");
	        	LOG.info("process handler deilery. The consumer tag is {},the routing key is {}, the msg is {} ", new Object[] {consumerTag , envelope.getRoutingKey() , msg});
	            messages.add(msg);
	            channel.basicAck(envelope.getDeliveryTag(), false);
	        }
	        //consumer_cancel_notify = true, 从 broke 删除queue, 好像这个方法没有调用， 不知道为什么??
	        //调用的是handlerCancel
	        @Override
	        public void handleCancelOk(String consumerTag) {
	        	LOG.info("process handler cancel OK. The consumer tag is {} ", consumerTag );
	            cancelLatch.countDown();
	        }

	        
	        @Override
			public void handleCancel(String consumerTag) throws IOException {
	        	LOG.info("handler cancel. The consumer tag is {} ", consumerTag );
			}

			String nextDelivery(long timeoutInMs) throws InterruptedException {
	            return messages.poll(timeoutInMs, TimeUnit.MILLISECONDS);
	        }

	    }
}
