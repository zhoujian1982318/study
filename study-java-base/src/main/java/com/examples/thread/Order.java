package com.examples.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Order implements Delayed {
	
	private long generatedTime;
	
	private long expiredTime;
	
	private String hGeneratedTime;
	
	private String hExpiredTime;
	
	private String item;
	
	private SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Order(long gTime,  long eTime, String item) {
		generatedTime = gTime;
		expiredTime = eTime;
		this.item = item;
		hGeneratedTime = sf.format(new Date(gTime));
		hExpiredTime = sf.format(new Date(expiredTime));
	}
	
	

	@Override
	public int compareTo(Delayed o) {
		return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		 return unit.convert(expiredTime - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
	}



	public String gethGeneratedTime() {
		return hGeneratedTime;
	}



	public void sethGeneratedTime(String hGeneratedTime) {
		this.hGeneratedTime = hGeneratedTime;
	}



	public String gethExpiredTime() {
		return hExpiredTime;
	}



	public void sethExpiredTime(String hExpiredTime) {
		this.hExpiredTime = hExpiredTime;
	}



	public String getItem() {
		return item;
	}



	public void setItem(String item) {
		this.item = item;
	}
	
	

}
