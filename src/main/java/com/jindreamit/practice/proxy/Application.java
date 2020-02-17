package com.jindreamit.practice.proxy;

import com.jindreamit.practice.proxy.server.ProxyServer;
import lombok.SneakyThrows;

public class Application {

    @SneakyThrows
    public static void main(String[] args) {
        ProxyServer proxyServer=new ProxyServer(11223,2);
        proxyServer.start();
    }
}
