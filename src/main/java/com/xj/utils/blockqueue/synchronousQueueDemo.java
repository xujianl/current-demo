package com.xj.utils.blockqueue;

/**
 * @author 徐建
 * @PackageName:com.xj.utils.blockqueue
 * @ClassName: synchronousQueueDemo
 * @Description:
 * @date 2020/3/14 21:44
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 个人定制版  不存储队列，即单个元素的队列
 */
public class synchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + " put 2");
                blockingQueue.put("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
                try {
                      TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName() + " take" + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                      TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName() + " take" + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        },"t2").start();
    }
}
