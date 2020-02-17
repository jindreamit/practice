package com.jindreamit.practice.socket;

import com.jindreamit.practice.socket.chat.server.ChatServer;

public class Application {

    public static void main(String[] args) throws Exception{
        ChatServer chatServer=new ChatServer(9999);
        new Thread(()->{
            try {
                chatServer.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
