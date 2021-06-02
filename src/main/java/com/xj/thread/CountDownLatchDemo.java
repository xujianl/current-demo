package com.xj.thread;

import com.xj.utils.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: CountDownLatchDemo
 * @Description:
 * @date 2020/3/9 22:31
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("第三次修改");
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 国被灭");
                countDownLatch.countDown();
            }, CountryEnum.foreachCountry(i).getRetMessage()).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " 秦王朝一统江湖");
    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 同学离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " 班长关门");
    }
}
