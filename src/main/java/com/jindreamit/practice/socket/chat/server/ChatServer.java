package com.jindreamit.practice.socket.chat.server;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ChatServer {

    private Lock lock=new ReentrantLock();

    private final List<SocketHandler> clients=new LinkedList<>();

    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void start()throws Exception{
        ServerSocket chatServer=new ServerSocket(port);

        new Thread(()->{
            while (true) {
                lock.lock();
                try {
                    log.info("已连接客户端数目: {} ",this.clients.size());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(5*1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        while (true){
            SocketHandler client=new SocketHandler(chatServer.accept(), UUID.randomUUID().toString(), this, lock);
            client.start();
            System.out.println("客户端已连接.");
            lock.lock();
            try {
                this.clients.add(client);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public void sendToChatRoom(String clientId,String msg){
        lock.lock();
        try {
            this.clients.forEach(otherClient -> {
                if (!otherClient.getClientId().equals(clientId)) {
                    otherClient.sendMessage(msg);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void sendTip(String tip){
        lock.lock();
        try {
            this.clients.forEach(client -> {
                client.sendMessage(tip);
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}