package com.examples.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayQueueTest {

	private static Logger LOG = LoggerFactory.getLogger(DelayQueueTest.class);
	private static ExecutorService excute = Executors.newFixedThreadPool(1);
	private static DelayQueue<Order> q = new DelayQueue<>();

	public static void main(String[] args) {
		DelayQueueTest t = new DelayQueueTest();
		t.init();

		excute.submit(() -> t.process());
		
		t.addOtherOrders();
	}

	private void addOtherOrders() {
		try {
			Thread.sleep(10*1000);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time;
			time = sf.parse("2018-05-10 17:00:00");
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			long genTime = cal.getTime().getTime();
			// after 20 minutes . the order is expired.
			long eTime = genTime + 1000 * 60 * 1;
			Order order = new Order(genTime, eTime, "order10");
			q.offer(order);
		}catch (ParseException | InterruptedException e) {
			LOG.error("inti q error", e);
		}
		
	}

	public void process() {
		try {
			for (;;) {
				Order order = q.take();
				if (q.size() < 5) {
					// todo add new order to q;
					LOG.info("need to add new order to the queue");
				}
				String item = order.getItem();
				String hGenTime = order.gethGeneratedTime();
				String hExTime = order.gethExpiredTime();
				LOG.info(
						"start process order. set order invalid.the order item is {}. the generate time is {}, the expired time is {}",
						new Object[] { item, hGenTime, hExTime });
			}
		} catch (InterruptedException e) {
			LOG.error("process error. ", e);
		}
	}

	private void init() {

		try {
			// prepare the data, load 10 order 按顺序 to delay queue.
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time;
			time = sf.parse("2018-05-10 17:00:00");
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			for (int i = 0; i < 10; i++) {
				long genTime = cal.getTime().getTime();
				// after 20 minutes . the order is expired.
				long eTime = genTime + 1000 * 60 * 1;
				Order order = new Order(genTime, eTime, "order" + i);
				q.offer(order);
				cal.add(Calendar.MINUTE, 20);
			}
		} catch (ParseException e) {
			LOG.error("inti q error", e);
		}

	}

}
