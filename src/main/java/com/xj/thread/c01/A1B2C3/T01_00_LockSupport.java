package com.xj.thread.c01.A1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 徐建
 * @PackageName:com.xj.thread.c01.A1B2C3
 * @ClassName: T01_00_LockSupport
 * @Description:用两个线程，一个输出数字，一个输出字母，交替输出1A2B3C....
 * @date 2020/4/12 22:48
 */
public class T01_00_LockSupport {
    static Thread t1;
    static Thread t2;

    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        t1 = new Thread(() -> {
            for (char c : aI){
                System.out.println(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");
        t2 = new Thread(() -> {
            for (char c : aC){
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t1);
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
