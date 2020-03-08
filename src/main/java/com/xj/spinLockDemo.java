package com.xj;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: spinLockDemo
 * @Description:
 * @date 2020/3/8 19:08
 */
public class spinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        System.out.println(Thread.currentThread().getName() + "  come in");
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null,thread)){

        };
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName() + " invoked myUnLock" );
    }
    public static void main(String[] args) {
        spinLockDemo spinLockDemo = new spinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                  TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                  e.printStackTrace();
             }
            spinLockDemo.myUnLock();
        },"t1").start();

        try {
              TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
              e.printStackTrace();
         }
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                  TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                  e.printStackTrace();
             }
            spinLockDemo.myUnLock();
        },"t2").start();
    }
}
