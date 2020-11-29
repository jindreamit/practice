package com.jindreamit.practice.thread.pool;

import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {

    private int threadSize;

    private BlockingQueue<Runnable> taskQueue;

    private WorkThread[] threads;

    private int lastIndexOfArr;

    public ThreadPool(int threadSize) {
        this.threadSize = threadSize;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = new WorkThread[this.threadSize];
        this.lastIndexOfArr = 0;
    }

    public void submit(Runnable task) {
        this.taskQueue.add(task);
        if (this.lastIndexOfArr >= threads.length) return;
        if (null==this.threads[this.lastIndexOfArr]){
            this.threads[this.lastIndexOfArr]=new WorkThread();
            this.threads[this.lastIndexOfArr].setName("Worker thread - "+this.lastIndexOfArr);
            this.threads[this.lastIndexOfArr].setRunning(true);
            this.threads[this.lastIndexOfArr].start();
            this.lastIndexOfArr+=1;
        }
    }


    private class WorkThread extends Thread {

        private boolean running = false;

        public void setRunning(boolean running) {
            this.running = running;
        }

        public boolean isRunning() {
            return running;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Runnable runnable = taskQueue.take();
                    System.out.println("我是线程池 "+this.getName());
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        for (WorkThread thread : threads) {
            thread.setRunning(false);
        }
    }
}
