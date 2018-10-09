package com.examples.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynStaticTest {
	
	public static SynStaticTest staticIn = new SynStaticTest();
	
	//静态方法，类似于对 SynStaticTest.class 对象加锁
	public synchronized static void fun() {
		for (int i = 0; i < 20; i++) {
			System.out.print(i + " ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized static void fun1() {
		for (int i = 0; i < 10; i++) {
			System.out.print(i + " ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void nonstaticFun() {
		for (int i = 0; i < 10; i++) {
			System.out.print(i + " ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public static void main(String[] args) {
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				SynStaticTest.fun();

			}
		};

		Runnable run2 = new Runnable() {
			@Override
			public void run() {
				SynStaticTest.fun1();

			}
		};
		SynStaticTest test = new SynStaticTest();
		//test.fun1();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(run1);
		executorService.execute(run2);
		executorService.execute(()->test.nonstaticFun());
		//executorService.execute(()->SynStaticTest.staticIn.fun1());
		//executorService.execute(()->SynStaticTest.staticIn.fun());
		executorService.shutdown();
	}

}
