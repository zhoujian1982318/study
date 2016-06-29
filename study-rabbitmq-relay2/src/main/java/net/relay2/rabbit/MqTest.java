/**
 * 
 */
package net.relay2.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author jzhou
 * 
 */
public class MqTest {

	private static final String EXCHANGE_NAME = "study-exchange";

	public static void main(String[] argv) throws java.io.IOException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.10.20.171");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		int number = channel.getChannelNumber();
		System.out.println("number is :"+number);
		//channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
		for(int i=10; i<20; i++){
			String message = getMessage(i);
			channel.basicPublish("fanout-exchange", "",  
					            new AMQP.BasicProperties.Builder()
            	                .deliveryMode(2)
            	                .build(), 
                                message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		channel.close();
		connection.close();
	}

	private static String getMessage(int i) {
		return i+" message";
	}

}
