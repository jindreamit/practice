package com.jindreamit.practice.current.blocking.queue;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T extends Object> {

    private int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    private Queue<T> queue = new LinkedList<>();

    public synchronized void produce(T model) {
        try {
            while (capacity == queue.size())
                this.wait();
        }catch (Exception e){
            e.printStackTrace();
//            this.notifyAll();
        }
        queue.offer(model);
    }

    public synchronized T consume(){
        try {
            while (queue.isEmpty())
                this.wait();
        }catch (InterruptedException e){
            e.printStackTrace();
//            this.notifyAll();
        }finally {
            return queue.poll();
        }
    }

    public synchronized int getCurrentSize(){
        return queue.size();
    }
}
