package com.ashitem;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        //创建ThreadLocal对象
        ThreadLocal tl=new ThreadLocal<>();
        //开启两个线程
        new Thread(()->{
            tl.set("张三");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"蓝色").start();
        new Thread(()->{
            tl.set("李四");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"黄色").start();

    }
}
