package com.jindreamit.practice.current.blocking;

import com.jindreamit.practice.current.blocking.consumer.Consumer;
import com.jindreamit.practice.current.blocking.monitor.Monitor;
import com.jindreamit.practice.current.blocking.producer.Producer;
import com.jindreamit.practice.current.blocking.queue.Storage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {
        Storage<Producer.Model> storage=new Storage<>(20);
        Producer p1=new Producer(storage,"甲");
        Producer p2=new Producer(storage,"乙");
        Producer p3=new Producer(storage,"丙");
        Producer p4=new Producer(storage,"丁");

        Consumer<Producer.Model> c1=new Consumer<>(storage,"胡歌");
        Consumer<Producer.Model> c2=new Consumer<>(storage,"梁朝伟");
        Monitor monitor=new Monitor(storage);
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.submit(monitor);
        executorService.submit(p1);
        executorService.submit(p2);
        executorService.submit(p3);
        executorService.submit(p4);
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        executorService.submit(c1);
        executorService.submit(c2);
    }
}
