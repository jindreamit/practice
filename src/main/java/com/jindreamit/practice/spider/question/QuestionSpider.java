package com.jindreamit.practice.spider.question;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jindreamit.practice.utils.spider.RandomUserAgentUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import redis.clients.jedis.Jedis;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QuestionSpider {

    private static final String LI_SELECTOR="#test_cat_50 > table > tbody > tr > td > ul > li > a";

    @SneakyThrows
    public static void main(String[] args) {
        Long i=0L;
        while (true) {
            try {
                String booksURL = "http://www.51mokao.com/testlist?id=50";
                Map<String, String> map = new HashMap<>();
                Jsoup.connect(booksURL).userAgent(RandomUserAgentUtil.getRandomUserAgent()).timeout(60*1000).get().
                        select(LI_SELECTOR)
                        .forEach(e -> {
                            map.put(e.attr("href"), e.text());
                        });
                Jedis jedis = new Jedis("jindreamit.com");
                jedis.set("books_url", JSONObject.toJSONString(map));
                log.info("第{}次成功",i++);
                Thread.sleep(6*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        Jedis jedis=new Jedis("jindreamit.com");
//        HashMap<String,String> map=JSONObject.parseObject(jedis.get("books_url"),HashMap.class);
//        map.forEach((a,b)->{
//            System.out.println(a);
//            System.out.println(b);
//        });
//        FileOutputStream out=new FileOutputStream("data.json");
//
//
//        out.flush();
//        out.close();
//        System.out.println(map);
//        String URL="http://www.51mokao.com/testpractice?id=655120";
//        Jsoup.connect(URL)
//                .userAgent(RandomUserAgentUtil.getRandomUserAgent())
//                .timeout(60*1000)
//                .get()
//                .select("td.title")
//                .forEach(e->{
//                    log.info("题目： {}",e.select("h3").text().trim().replaceAll("&nbsp;",""));
//                    log.info("选项");
//                    e.select("p").stream().map(op->op.select("label").text()).forEach(System.out::println);
//                });
//        System.out.println(Jsoup.connect(URL)
//                .userAgent(RandomUserAgentUtil.getRandomUserAgent())
//                .method(Connection.Method.POST)
//                .data("MK_Q6928389","1")
//                .data("MK_Q6928390","28290319")
//                .data("MK_Q6928396","")
//                .data("MK_Q6928397","")
//                .data("typ","")
//                .data("repeatSub","123")
//                .execute()
//                .body());

    }
}
