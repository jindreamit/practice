package com.jindreamit.practice.proxy.clent;

import sun.security.ssl.SSLServerSocketFactoryImpl;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClientRunnable implements Runnable {

    private static final String BLANK_STR = " ";

    private Socket client;

    public SocketClientRunnable(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.setSoTimeout(60 * 1000);
            InputStream in = client.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();
            boolean isHTTPS = "CONNECT".equals(line.split(BLANK_STR)[0]);
            String host = line.split(BLANK_STR)[1].split(":")[0];
            String port = line.split(BLANK_STR)[1].split(":")[1];
            while (line != null) {
                if (line.length() == 0)
                    break;
                if (!isHTTPS&&"Host:".equals(line.split(BLANK_STR)[0])) {
                    System.out.println("Host Line: " + line);
                    String[] tem = line.split(BLANK_STR)[1].split(":");
                    host = line.split(BLANK_STR)[1].split(":")[0];
                    port="80";
                    if (tem.length > 1) {
                        port = line.split(BLANK_STR)[1].split(":")[1];
                    }
                }

                sb.append(line).append("\r\n");
                sb.append("MY PROXY SERVER HEADER: TEST CONNECTION").append("\r\n");

                line = bufferedReader.readLine();
            }
            System.out.println(sb.toString());
            System.out.println(host);
            System.out.println(port);
            Socket proxySocket = new Socket(host, Integer.parseInt(port));


            proxySocket.setSoTimeout(60 * 1000);
            System.out.println("连接成功 :"+proxySocket.isConnected());
//            System.out.println(proxySocket.isConnected());
            OutputStream proxyOut = proxySocket.getOutputStream();
            InputStream proxyIn = proxySocket.getInputStream();
            OutputStream clientOut = client.getOutputStream();
            if (isHTTPS) {
                clientOut.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                clientOut.flush();
            }else {
                proxyOut.write(sb.append("\r\n").toString().getBytes());
                int l;
                byte[] bytes = new byte[1024];
                BufferedReader br=new BufferedReader(new InputStreamReader(proxyIn));
                while ((line=br.readLine())!=null){
                    System.out.println(line);
                    if (br.readLine().equals("")){
                        System.out.println(line);
                    }
                }

//                try {
//                    while (true) {
//                        l = proxyIn.read(bytes);
//                        System.out.println(l);
//                        clientOut.write(bytes, 0, l);
//                    }
//                } catch (Exception se) {
//                    se.printStackTrace();
//                    System.out.println("write success");
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}