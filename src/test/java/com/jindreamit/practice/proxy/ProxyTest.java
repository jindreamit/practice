package com.jindreamit.practice.proxy;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.net.Socket;

public class ProxyTest {

    @Test
    @SneakyThrows
    public void test(){
        System.out.println(Jsoup.connect("http://jindreamit.com/")
                .proxy("127.0.0.1",11223)
                .timeout(60*1000)
                .get());
    }
}
