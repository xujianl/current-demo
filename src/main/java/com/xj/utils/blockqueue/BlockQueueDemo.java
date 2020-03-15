package com.xj.utils.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: BlockQueueDemo
 * @Description:
 * @date 2020/3/14 20:53
 */

public class BlockQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("b");
//            blockingQueue.put("d");
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");
//        blockingQueue.add("d");
        System.out.println(blockingQueue.element());
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
//        blockingQueue.remove();


        blockingQueue.offer("1");
        blockingQueue.offer("2");
        blockingQueue.offer("3");
        System.out.println(blockingQueue.offer("4"));

        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        System.out.println(blockingQueue.poll());



    }
}
