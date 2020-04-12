package com.xj.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author 徐建
 * @PackageName:com.xj.thread
 * @ClassName: DeadLockDemo
 * @Description:死锁是指两个或两个以上的进程在执行过程中
 *  因争夺资源而造成的一种互相等待的现象，若无外力干涉他们将无法推进下去
 * @date 2020/4/12 19:37
 */
class HoldLockThread implements Runnable{
    String lockA;
    String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t 持有锁" + lockA + " 尝试获取锁" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t 持有锁" + lockB + " 尝试获取锁" + lockA);
            }
        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA,lockB),"lockAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"lockBBB").start();
    }
}
