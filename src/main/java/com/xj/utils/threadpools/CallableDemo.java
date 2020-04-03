package com.xj.utils.threadpools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 徐建
 * @PackageName:com.xj.utils.threadpools
 * @ClassName: CallableDemo
 * @Description:
 * @date 2020/3/21 11:11
 */

class MyThread implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "....... 进入callable");
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        Thread t1 =  new Thread(futureTask,"aaa");
        Thread t2 =  new Thread(futureTask,"bbb");
        t1.start();
        t2.start();

        while (!futureTask.isDone()){

        }

        System.out.println(Thread.currentThread().getName() + " ............");

        System.out.println("获得结果" + futureTask.get());
    }
}


