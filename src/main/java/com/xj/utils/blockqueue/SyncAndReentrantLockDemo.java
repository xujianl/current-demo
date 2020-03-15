package com.xj.utils.blockqueue;

/**
 * @author 徐建
 * @PackageName:com.xj.utils.blockqueue
 * @ClassName: SyncAndReentrantLockDemo
 * @Description:
 * @date 2020/3/15 17:32
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  sync和lock区别，lock有什么好处
 *  1 原始构成
 *    sync 是关键字，属于JVM层面
 *    ReenTrantLock是具体实现，是api层面
 *  2 使用方法
 *    sync 不需要手动释放锁   lock需要手动释放，否则会出现死锁
 *  3 等待是否可中断
 *    sync不可中断
 *    lock可以中断：1 设置超时方法
 *                 2 lockinterruptibly放代码块中，调用interrupt方法可中断
 *  4 加锁是否公平
 *     sync非公平锁
 *     lock默认非公平，但可通过构造方法传布尔值来设置，true为公平锁
 *  5 是否可设置多个contiditon
 *     sync无
 *     lock用来实现分组唤醒需要唤醒的线程，可以精确唤醒，而不是像sync只能随机唤醒一个或唤醒全部线程
 *  举例题目 ：多线程之间按顺序调用  实现A->B->C启动，要求如下：
 *  AA打印5次，BB打印10次，CC打印15次
 *  紧接着
 *  AA打印5次，BB打印10次，CC打印15次
 *  ........
 *  循环来10次
 */

class ShareResoure{
    private int number = 1; // AA:1 BB:2 CC:3
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            while (number != 1){
                c1.await();
            }
            for (int i = 1; i <=5 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 2;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            while (number != 2){
                c2.await();
            }
            for (int i = 1; i <=10 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 3;
            c3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            while (number != 3){
                c3.await();
            }
            for (int i = 1; i <=15 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 1;
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResoure shareResoure = new ShareResoure();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareResoure.print5();
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareResoure.print10();
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareResoure.print15();
            }
        },"CC").start();
    }
}
