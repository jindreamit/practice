package com.jindreamit.practice.proxy;

import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.net.Socket;

public class ProxyTest {

    @Test
    @SneakyThrows
    public void test() {
        Connection connection = Jsoup.connect("http://www.gov.cn/")
                .proxy("127.0.0.1", 11223)
                .timeout(60 * 1000);
        connection.method(Connection.Method.GET);
        Connection.Response response=connection.execute();
        System.out.println(response.headers());
    }
}
