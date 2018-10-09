package com.examples.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadState {


    //sleep block, sleep 进入的 TIMED_WAITING. 设置了时间
    static class SleepBlock implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //sleepWaiting();
        //blocked();
        waiting();
    }

    static class SyncBlock implements  Runnable {

        private Object lock ;

        public SyncBlock(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {

            synchronized (lock) {
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void sleepWaiting() {
        try{
            Thread t = new Thread(new SleepBlock());
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void blocked()  {

        Object lock = new Object();

        Thread t = new Thread(new SyncBlock(lock));
        t.start();
        //sleep 2 seconds. syncBlock will get the lock
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //syncBlock thread get the lock . main thread enter blocked(waiting for monitor entry)
        synchronized (lock){
            System.out.println("get the concurrent lock...");
        }

    }


    private static void waiting(){

        final ReentrantLock lock = new ReentrantLock();

        final Condition condition = lock.newCondition();

        ExecutorService executor = Executors.newFixedThreadPool(5);

       executor.execute(()->{
            lock.lock();
            try {
                System.out.println(" runnable 1 get lock ");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
               lock.unlock();
            }

        } );

//        executor.execute(()->{
//           // try {
//                try {
//                    condition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//           // }finally {
//               // lock.unlock();
//          //  }
//        } );

        executor.shutdown();
    }
}
