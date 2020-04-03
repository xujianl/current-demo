package com.xj.utils.blockqueue;

/**
 * @author 徐建
 * @PackageName:com.xj.utils.blockqueue
 * @ClassName: ProdConsumerBlockQueueDemo
 * @Description:
 * @date 2020/3/15 20:01
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列版生产消费者模式
 * Volatile/CAS/AtomicInteger/BlockQueue/线程交互/原子引用
 */
class MyResoure{
    private volatile boolean FLAG = true;
    private BlockingQueue<String> blockingQueue = null;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public MyResoure(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void MyProd(){
        String data = "";
        Boolean result;
        while (FLAG){
            try {
                data = atomicInteger.incrementAndGet() + "";
                result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                if (result){
                    System.out.println(Thread.currentThread().getName() + "\t 插入" + data + "成功");
                }else{
                    System.out.println(Thread.currentThread().getName() + "\t 插入" + data + "失败");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 大老板叫停标识FLAG=false");
    }

    public void MyConsumer(){
        String result = "";
        while (FLAG){
            try {
                result = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if (null == result || result.equalsIgnoreCase("")){
                    FLAG = false;
                    System.out.println(Thread.currentThread().getName() + "\t" + "超过2s没获取到蛋糕，自动停止");
                    System.out.println();
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "成功获取到" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        this.FLAG = false;
    }
}
public class ProdConsumerBlockQueueDemo {
    public static void main(String[] args) {
        MyResoure myResoure = new MyResoure(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "生产者线程启动");
            myResoure.MyProd();
        },"Prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "消费者线程启动");
            System.out.println();
            System.out.println();
            myResoure.MyConsumer();
        },"Consumer").start();

        try {
              TimeUnit.SECONDS.sleep(5);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(Thread.currentThread().getName() + "\t" + "main线程大老板叫停");
            myResoure.stop();
        } catch (InterruptedException e) {
              e.printStackTrace();
         }
    }
}
