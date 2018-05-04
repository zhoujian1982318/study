package com.examples.rabbit.producer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class EmitNews {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmitNews.class);
	
	private static String [] newsTopic = new String[] {"sport", "finance", "ent",  "photo", "sport-photo", "finance-photo", "ent-photo" };
	
	private static Random rnd = new Random();
	
	private static final String EXCHANGE_NEWS_NAME = "news";
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.20.139");
		factory.setPort(5672);
		//ExecutorService executor ,可以传入 executor, 默认是线程池大小 为 5
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		Exchange.DeclareOk declareOk = channel.exchangeDeclare(EXCHANGE_NEWS_NAME, "topic");
		LOG.info("declare no-durable topic  exchange 'news' return is {}", declareOk);
		
		String[] msgPair = getMessage();

        channel.basicPublish(EXCHANGE_NEWS_NAME, msgPair[0], MessageProperties.TEXT_PLAIN, msgPair[1].getBytes());
        LOG.info("Send the message, the routing key is {},  message is {} " , msgPair[0] , msgPair[1] );
        
        channel.close();
        connection.close();
    }
	
	private  static String[] getMessage() {
		int idx = rnd.nextInt(6);
		String topic = newsTopic[idx];
		String[] pairs = new String[2];
		switch(topic) {
			case "sport": 
					pairs[0]="sport."+idx;
					pairs[1]="the sport news, the random number is "+ idx;
					break;
			case "finance":
					pairs[0]= "finance."+idx;
					pairs[1]= "the finance news, the random number is "+ idx;
					break;
			case "ent": 
				pairs[0]="ent."+idx;
				pairs[1]="the ent news, the random number is "+ idx;
				break;
		    case "photo":
				pairs[0]="random.photo."+idx;
				pairs[1]="the random photo news, the random number is "+ idx;
				break;
		    case "sport-photo":
				pairs[0]="sport.photo."+idx;
				pairs[1]="the sport photo news, the random number is "+ idx;
				break;
		    case "finance-photo":
				pairs[0]="finance.photo."+idx;
				pairs[1]="the finance photo news, the random number is "+ idx;
				break;
		    case "ent-photo":
				pairs[0]="ent.photo."+idx;
				pairs[1]="the entertainment photo news, the random number is "+ idx;
				break;
		    default :
		    	break;
		};
		return pairs;
	}
}
