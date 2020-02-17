package com.jindreamit.practice.socket.chat.client;

import com.jindreamit.practice.socket.chat.message.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ChatClient extends Thread{

    private int port;

    private final Socket socket;

    private final InputStream in;

    private final OutputStream out;

    public ChatClient(String ip, int port)throws Exception{
        this.socket=new Socket(ip,port);
        this.in=this.socket.getInputStream();
        this.out=this.socket.getOutputStream();
    }

    public void sendMessage(Message message)throws Exception{
        this.out.write(message.getMessageBytes());
    }

    @Override
    public void run() {
        byte []buffBytes=new byte[1024];
        int length;
        while (true){
            try {
                length=this.in.read(buffBytes);
                if (length!=-1) {
                    System.out.println("收到来自服务器的消息: " + new String(buffBytes, 0, length));
                }
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
