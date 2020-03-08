package com.xj;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: SingletonDemo
 * @Description:
 * @date 2020/3/1 8:35
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+ "我是构建方法 ");
    }

    public static SingletonDemo getInstance() {
        if (instance == null){
            synchronized (SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
/*
        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
*/
        for (int i = 1; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
