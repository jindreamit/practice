package com.jindreamit.practice.current.blocking.consumer;

import com.jindreamit.practice.current.blocking.queue.Storage;

public class Consumer<T> implements Runnable{

    static int consumeNumber=0;

    private Storage<T> storage;

    private String name;

    public Consumer(Storage<T> storage,String name){
        this.storage=storage;
        this.name=name;
    }


    @Override
    public void run() {
        while (true){
            T model=this.storage.consume();
            System.out.println(this.name+" 消费成功: "+ model);
            try {
                Thread.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
