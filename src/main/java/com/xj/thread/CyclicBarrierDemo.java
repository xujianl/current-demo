package com.xj.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: CyclicBarrierDemo
 * @Description:
 * @date 2020/3/11 22:03
 */

/**
 * 循环栅栏，线程达到设定值，继续下一步行动
 * 会让所有线程都等待完成后才会继续下一步行动
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println(Thread.currentThread().getName() + " 集齐龙珠，召唤神龙");
        });
        for (int i = 1; i <= 7; i++) {
            final int tempint = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 收集第" + tempint + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
