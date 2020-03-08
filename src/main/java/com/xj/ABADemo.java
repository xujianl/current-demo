package com.xj;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: ABAdEMO
 * @Description:
 * @date 2020/3/1 21:02
 */
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        System.out.println("//////////////////ABA问题的产生//////////////////");
        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();

        new Thread(() -> {
            //暂停1s，保证上面他进行一次ABA
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100,2019) + "\t" + atomicReference.get());

        },"t2").start();
        try {
              TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
              e.printStackTrace();
         }
        System.out.println("//////////////////ABA问题的产生//////////////////");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t" + "第1次版本号" + stamp);
            try {
                  TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                  e.printStackTrace();
             }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t" + "第2次版本号" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t" + "第3次版本号" + atomicStampedReference.getStamp());
        },"t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t" + "第1次版本号" + stamp);
            try {
                  TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                  e.printStackTrace();
             }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t" + "修改成功否" + result + "当前实际版本号：" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t" + atomicStampedReference.getReference());
        },"t4").start();
    }
}
