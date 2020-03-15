package com.xj.utils.blockqueue;

/**
 * @author 徐建
 * @PackageName:com.xj.utils.blockqueue
 * @ClassName: BlockQueueTrantionalDemo
 * @Description:
 * @date 2020/3/15 9:33
 */

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：对一个整型变量，初始值为0，两个线程通过是进行加1和减1操作，各循环5次
 * 1 线程  操作 资源类 MyData
 * 2 判断   动作     执行
 * 3 避免虚假唤醒，要用while    4个线程用if会有问题
 */
class MyData{
    private int tmpValue = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increase(){
        lock.lock();
        try {
            while (tmpValue != 0){
                condition.await();
            }
            tmpValue ++;
            System.out.println(Thread.currentThread().getName() + " " + tmpValue);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void decress(){
        lock.lock();
        try {
            while (tmpValue == 0){
                condition.await();
            }
            tmpValue --;
            System.out.println(Thread.currentThread().getName() + " " + tmpValue);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
public class ProdConsumerTrantionalDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                myData.increase();
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                myData.decress();
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                myData.increase();
            }
        },"CC").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                myData.decress();
            }
        },"DD").start();
    }
}
