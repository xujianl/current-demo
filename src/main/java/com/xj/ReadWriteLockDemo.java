package com.xj;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: ReadWriteLockDemo
 * @Description:
 * @date 2020/3/8 20:52
 */

/**
 * 读读-能共存
 * 读写-不能共存
 * 写写-不能共存
 * 写锁：原子+独占
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();

    ReentrantReadWriteLock rlwLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){
        rlwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入" + key);
            map.put(key,value);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 写入完成！");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rlwLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rlwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取");
            Object result = map.get(key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 读取完成 " + result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rlwLock.readLock().unlock();
        }
    }
    public void cleanCache(){
        map.clear();
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp+ "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }, String.valueOf(i)).start();
        }
    }
}
