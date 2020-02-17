package com.jindreamit.practice.leetcode.thread.count;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {

    private Lock lock = new ReentrantLock();

    private CountDownLatch count1 = new CountDownLatch(1);


    private boolean f = true;

    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo() throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                System.out.println("foo");
                count1.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void bar() throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                count1.await();
                System.out.println("bar");
                count1 = new CountDownLatch(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        com.jindreamit.practice.leetcode.thread.lock.FooBar fooBar = new com.jindreamit.practice.leetcode.thread.lock.FooBar(2);

        new Thread(() -> {
            try {
                fooBar.foo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fooBar.bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
