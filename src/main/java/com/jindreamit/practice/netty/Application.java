package com.jindreamit.practice.netty;

import com.jindreamit.practice.socket.chat.message.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Application {

    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("127.0.0.1",11225);
        OutputStream out=socket.getOutputStream();
        out.write("hello".getBytes(StandardCharsets.UTF_8));
        InputStream in=socket.getInputStream();
        byte []bytes=new byte[1024];
        int l;
        while (true){
            l=in.read(bytes);
            if (l<bytes.length)break;
        }
        System.out.println(new String(bytes,0,l));
    }



}
