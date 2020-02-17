package com.jindreamit.practice.current.thread;

import com.jindreamit.practice.current.thread.print.PrintMethods;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) throws Exception {
        PrintMethods printMethods = new PrintMethods();


        new Thread(() -> {
            while (true)
                synchronized (printMethods) {
                    log.info("Thread B get lock");
                    log.info("Thread B 我需要 Flag = 2,现在 Flag = {}", printMethods.getFlag());
                    if (printMethods.getFlag() == 2) {
                        printMethods.printB();
                        printMethods.addFlag();
                        printMethods.resetFlag();
//                    printMethods.notifyAll();
//                    break;
                    }
                    try {
                        log.info("Thread B 唤醒所有需要 printMethods 的线程");
                        printMethods.notifyAll();
                        log.info("Thread B release lock");
                        printMethods.wait();
                        log.info("Thread B 被唤醒");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            while (true)
                synchronized (printMethods) {
                    log.info("Thread A get lock");
                    log.info("Thread A 我需要 Flag = 1,现在 Flag = {}", printMethods.getFlag());
                    if (printMethods.getFlag() == 1) {
                        printMethods.printA();
                        printMethods.addFlag();
//                        printMethods.notifyAll();
//                        break;
                    }
                    log.info("Thread A 唤醒所有需要 printMethods 的线程");
                    printMethods.notifyAll();
                    try {
                        log.info("Thread A release lock");
                        printMethods.wait();
                        log.info("Thread A 被唤醒");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();

//        new Thread(() -> {
//            while (true)
//            synchronized (printMethods) {
//                log.info("C get lock");
//                log.info("Flag {}",printMethods.getFlag());
//                if (printMethods.getFlag() == 3) {
//                    printMethods.printC();
//                    printMethods.resetFlag();
//                    break;
//                } else {
//                    printMethods.notifyAll();
//                    try {
//                        log.info("C release lock");
//                        printMethods.wait();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }
}
