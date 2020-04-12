package com.xj.thread.c01.A1B2C3;

/**
 * @author 徐建
 * @PackageName:com.xj.thread.c01.A1B2C3
 * @ClassName: T02_00_Cas
 * @Description:自旋锁方案
 * @date 2020/4/12 23:03
 */
public class T02_00_Cas {
    enum ReadyToRun {T1,T2};
    static volatile ReadyToRun r = ReadyToRun.T1;

    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        new Thread(() -> {
            for(char c : aI){
                while (r != ReadyToRun.T1){}
                System.out.println(c);
                r = ReadyToRun.T2;
            }
        },"t1").start();
        new Thread(() -> {
            for(char c : aC){
                while (r != ReadyToRun.T2){}
                System.out.println(c);
                r = ReadyToRun.T1;
            }
        },"t2").start();
    }
}
