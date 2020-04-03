package com.xj.utils.threadpools;

import java.util.concurrent.*;

/**
 * @author 徐建
 * @PackageName:com.xj.utils.threadpools
 * @ClassName: MyThreadPoolDemo
 * @Description:
 * @date 2020/4/3 22:18
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for (int i = 1; i <= 9; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }

    }
}
