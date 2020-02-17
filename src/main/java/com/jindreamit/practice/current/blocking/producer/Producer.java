package com.jindreamit.practice.current.blocking.producer;

import com.jindreamit.practice.current.blocking.queue.Storage;

public class Producer implements Runnable {

    static private int produceNumber;

    private Storage<Model> storage;

    private String name;

    public Producer(Storage<Model> storage,String name) {
        this.storage = storage;
        this.name=name;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(500);
                this.storage.produce(new Model(this.name+"-"+i++));
                System.out.println(this.name+" 生产了第 "+i+" 个产品");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static final class Model {
        private String id;

        public Model(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Model{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
