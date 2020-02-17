package com.jindreamit.practice.proxy.server;

import com.jindreamit.practice.proxy.clent.SocketClientRunnable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer {

    private ServerSocket serverSocket;

    private int port;

    int clientsNumber;

    public ProxyServer(int port, int clientsNumber) throws Exception {
        this.port = port;
        this.clientsNumber = clientsNumber;
        this.serverSocket = new ServerSocket(this.port, clientsNumber);
    }

    public void start() throws Exception {
        while (true) {
//            Socket socket = serverSocket.accept();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////            System.out.println(socket.getRemoteSocketAddress());
//            String line = reader.readLine();
//            String realURL = null;
//            int i = 0;
//            while (null != line ) {
////                if (i == 0) {
////                    System.out.println(line);
////                }
//                i++;
////                if (line.substring(0,3).equals("GET"))
//                System.out.println(line);
//                line = reader.readLine();
//            }
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            String lines = "HTTP/1.1 200 OK\r\n" +
//                    "Content-Type:text/html;charset=utf-8\r\n" +
//                    "\r\n" +
//                    "hello\n";
//            bufferedWriter.write(lines);
//            bufferedWriter.flush();
//            bufferedWriter.close();
//            System.out.println("flush output stream");
//            System.out.println("=======================");
//            socket.close();
            new Thread(new SocketClientRunnable(serverSocket.accept())).start();
        }
    }


}
