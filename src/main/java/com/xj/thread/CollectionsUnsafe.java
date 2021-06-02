package com.xj.thread;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: CollectionsUnsafe
 * @Description:
 * @date 2020/3/8 16:50
 */

/**
 * 多线程下会出现null，线程加大，出现java.util.ConcurrentModificationException
 * 1 使用Collections.synchronizedList
 * 2 vector
 * 3 new CopyOnWriteArrayList<>()
 */
public class CollectionsUnsafe {
    public static void main(String[] args) {
        System.out.println("第四次修改");
        List<String> list = new CopyOnWriteArrayList<>();//new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
