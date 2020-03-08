package com.xj;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: VolatileDemo
 * @Description:
 * @date 2020/2/29 17:02
 */

class myData{
//    int num = 0;
    volatile  int num = 0;


    public  void addto60(){
        this.num = 60;
    }

    public void addPlus(){
        this.num++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}

//不保证原子性
public class VolatileDemo {
    public static void main(String[] args) {
        myData myData = new myData();
        for (int i = 1; i <= 20; i++) {
        new Thread(() -> {
            for (int j = 1; j <= 1000; j++) {
                myData.addPlus();
                myData.addMyAtomic();
            }
        }, String.valueOf(i)).start();
    }
    while (Thread.activeCount() >2){
        //main和gc线程占用两个
        Thread.yield();
    }
        System.out.println(Thread.currentThread().getName() + "获取num：" + myData.num);
        System.out.println(Thread.currentThread().getName() + "获取atomicInteger：" + myData.atomicInteger);
    }

    //保证可见性
    private static void SeeOKByVolatile() {
        myData myData = new myData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addto60();
            System.out.println(Thread.currentThread().getName()+ "获取num:" + myData.num);
        },"AAA").start();
        //.run() 会按顺序执行，起不到多线程作用

        while(myData.num == 0){

        }

        System.out.println(Thread.currentThread().getName()+ "获取修改后的num:" + myData.num);
    }
}
