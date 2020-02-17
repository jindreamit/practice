package com.jindreamit.practice.socket.chat.server;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Slf4j
public class SocketHandler extends Thread {

    private final Socket client;

    private final String id;

    private OutputStream out;

    private InputStream in;

    private final ChatServer chatServer;

    private final Lock chatServerLock;

    public SocketHandler(Socket client, String id, ChatServer chatServer, Lock chatServerLock) {
        this.client = client;
        this.id = id;
        this.chatServer = chatServer;
        this.chatServerLock = chatServerLock;
        try {
            this.out=client.getOutputStream();
            this.in=client.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        log.info(this.id+" run ");
        String msg;
        while (true){
            try {
                msg=getMessage();
                System.out.println(msg);
                if (null!=msg){
                    log.info(msg);
                    chatServer.sendToChatRoom(this.id,msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getClientId() {
        return id;
    }

    public void sendMessage(String message){
        try {
            this.out.write(message.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMessage(){
        byte[] bufferBytes =new byte[4];

        int length;
        try {
            ByteList byteList= new ByteList();
            while (true) {
                length = in.read(bufferBytes);
                log.info("message byte length{},message to String {} ",length,new String(byteList.getBytes()));
                if (length>0)
                    byteList.add(bufferBytes,0,length);
                else
                    break;
            }
            return new String(byteList.getBytes());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static class ByteList{

        public ByteList(){
            this.sumSize=0;
        }


        private List<byte[]> byteArrayList=new LinkedList<>();

        private int sumSize;

        public void add(byte []bytes){
            this.byteArrayList.add(bytes);
            this.sumSize+=bytes.length;
        }

        public void add(byte []bytes,int begin,int end){
            byte []tem=new byte[end-begin];
            int k=0;
            for (int i = begin; i < end; i++) {
                tem[k]=bytes[i];
            }
            this.byteArrayList.add(tem);
            this.sumSize+=tem.length;
        }

        public byte[] getBytes(){
            byte []bytes=new byte[this.sumSize];
            int k=0;
            for (byte []b:byteArrayList){
                for (byte value : b) {
                    bytes[k++] = value;
                }
            }
            return bytes;
        }

        public void clear(){
            this.byteArrayList.clear();
            this.sumSize=0;
        }

        public int getSumSize() {
            return sumSize;
        }
    }
}
