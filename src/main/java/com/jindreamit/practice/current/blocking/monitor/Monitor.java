package com.jindreamit.practice.current.blocking.monitor;

import com.jindreamit.practice.current.blocking.queue.Storage;

public class Monitor implements Runnable {

    private Storage storage;

    public Monitor(Storage storage){
        this.storage=storage;
    }

    @Override
    public void run() {

        while (true){
            try {
                System.out.println("Storage has "+this.storage.getCurrentSize()+" items.");
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
