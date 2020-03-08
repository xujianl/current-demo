package com.xj;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 徐建
 * @PackageName:com.xj
 * @ClassName: AtomicReferenceDemo
 * @Description:
 * @date 2020/3/1 20:44
 */

@Getter
@ToString
@AllArgsConstructor
class User{
    String name;
    int age;
}
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z2",22);
        User li4 = new User("li4",25);
        AtomicReference<User> atomicReference = new AtomicReference<User>();
        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3,li4) + "原子类型引用" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3,li4) + "原子类型引用" + atomicReference.get().toString());
    }
}
