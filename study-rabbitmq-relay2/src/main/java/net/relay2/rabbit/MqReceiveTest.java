/**
 * 
 */
package net.relay2.rabbit;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * @author jzhou
 *
 */
public class MqReceiveTest {

	 private static final String EXCHANGE_NAME = "fanout-exchange";
	 private static final String QUEUE_NAME = "fanout-queue";

	    public static void main(String[] argv)
	                  throws java.io.IOException,
	                  java.lang.InterruptedException {

	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("10.10.20.171");
	        factory.setPort(5672);
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
	//        String queueName = "mirror-queue";
//	        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);
//	        Map<String, Object> args = new HashMap<String, Object>();
//	        args.put("x-ha-policy", "all");
	        channel.queueDeclare(QUEUE_NAME,
                    true,  // durable
                    false, // non-exclusive to the connection
                    false, // non-auto-delete
                    null);
	        
	       channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "direct");

	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	        //QueueingConsumer consumer = new QueueingConsumer(channel);
	        
	        //channel.basicConsume(queueName, true, consumer);
	        Consumer consumer =  new DefaultConsumer(channel){
	        	
	        	@Override
                public void handleDelivery(String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
	        		System.out.println("consumerTag is "+consumerTag);
	        		String message = new String(body);
	        		System.out.println(" [channel-1] Received '" + message + "'");
	        	}

	        };
	        
	        channel.basicConsume(QUEUE_NAME, true, consumer);

//	        while (true) {
//	            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//	            String message = new String(delivery.getBody());
//
//	            System.out.println(" [x] Received '" + message + "'");
//	        }
	        
//	        Channel channel2 = connection.createChannel();
//	        
//	        Consumer consumer2 =  new DefaultConsumer(channel2){
//	        	
//	        	@Override
//                public void handleDelivery(String consumerTag,
//                        Envelope envelope,
//                        AMQP.BasicProperties properties,
//                        byte[] body) throws IOException {
//	        		System.out.println("consumerTag is "+consumerTag);
//	        		System.out.println("default exchange");
//	        		String message = new String(body);
//	        		System.out.println(" [channel-2] Received '" + message + "'");
//	        	}
//
//	        };
//	        
//	        channel2.basicConsume(QUEUE_NAME+"1", true, consumer2);
	    }

}
