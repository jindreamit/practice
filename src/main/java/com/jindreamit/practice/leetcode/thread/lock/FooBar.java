package com.jindreamit.practice.leetcode.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {

    private Lock lock = new ReentrantLock();

    private boolean f = true;

    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo() throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    lock.lock();
                    if (f) {
                        // printFoo.run() outputs "foo". Do not change or remove this line.
//                        printFoo.run();
                        System.out.println("foo");
                        f = false;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }

    }

    public void bar() throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (true)
                try {
                    lock.lock();
                    if (!f) {
                        // printBar.run() outputs "bar". Do not change or remove this line.
//                        printBar.run();
                        System.out.println("bar");
                        f = true;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(4);

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
