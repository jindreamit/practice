package com.jindreamit.practice.socket;

import com.jindreamit.practice.socket.chat.client.ChatClient;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

public class ChatServerTest {


    @Test
    public void test(){
        System.out.println("100000000000000000000".length());
        System.out.println("100000000000000000000");
        System.out.println(Integer.toBinaryString(1024 * 1024));
    }

    public static void main(String[] args) throws Exception{
        new Thread(()->{
            try {
                ChatClient chatClient = new ChatClient("127.0.0.1", 9999);
                chatClient.start();
                int i=0;
                while (true) {
//                    chatClient.sendMessage("   哈哈哈哈哈");
                    Thread.sleep(2*60 * 1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();


//        new Thread(()->{
//            try {
//                ChatClient chatClient = new ChatClient("127.0.0.1", 9999);
//                chatClient.start();
//                int i=0;
//                while (true) {
//                    chatClient.sendMessage("是吗 "+i);
//                    Thread.sleep(2 * 1000);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }).start();
//
//        new Thread(()->{
//            try {
//                ChatClient chatClient = new ChatClient("127.0.0.1", 9999);
//                chatClient.start();
//                int i=0;
//                while (true) {
//                    chatClient.sendMessage("哦哦哦哦哦哦哦哦 "+i);
//                    Thread.sleep(2 * 1000);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }).start();
    }
}
