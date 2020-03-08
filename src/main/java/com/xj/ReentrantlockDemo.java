package com.xj;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: ReentrantlockDemo
 * @Description:
 * @date 2020/3/8 18:18
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronize和reentrantlock是可重入锁
 */
class phone implements Runnable{
    public synchronized void method1(){
        System.out.println(Thread.currentThread().getName() + "  invoke method1");
        method2();
    }

    public synchronized void method2(){
        System.out.println(Thread.currentThread().getName() + "  invoke method2");
    }

    ///////////////////////////////////////////////////////////////////
    Lock lock = new ReentrantLock();
    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  invoke get");
            set();
        }finally {
           lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  invoke set");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
           lock.unlock();
        }
    }
    @Override
    public void run() {
            get();
    }
}
public class ReentrantlockDemo {
    public static void main(String[] args) {
        phone phone = new phone();
        new Thread(() -> {
            phone.method1();
        },"t1").start();

        new Thread(() -> {
            phone.method1();
        },"t2").start();

        try {
              TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
              e.printStackTrace();
         }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        Thread thread = new Thread(phone,"t3");
        thread.start();
        Thread thread1 = new Thread(phone,"t4");
        thread1.start();

    }
}
