package com.jindreamit.practice.spider.douban.movie;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jindreamit.practice.utils.spider.RandomUserAgentUtil;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieSpider {
    @SneakyThrows
    public static void main(String[] args) {
        Jedis jedis = new Jedis("jindreamit.com");
        String text = Jsoup.connect("https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0")
                .userAgent(RandomUserAgentUtil.getRandomUserAgent())
                .header("Referer", "https://movie.douban.com/")
                .proxy("117.88.176.48",3000)
                .ignoreContentType(true)
                .timeout(10 * 1000)
                .get().body().text();
        JSONObject movieJSON = JSONObject.parseObject(text);
        System.out.println(movieJSON);
        JSONArray array = movieJSON.getJSONArray("subjects");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject movie = array.getJSONObject(i);
            String coverURL = movie.getString("cover");
            String title = movie.getString("title");
            System.out.println(title);
            System.out.println(coverURL);
            Element info = Jsoup.connect(movie.getString("url"))
                    .userAgent(RandomUserAgentUtil.getRandomUserAgent())
                    .header("Referer", "https://movie.douban.com/")
                    .ignoreContentType(true)
                    .get().select("div.info").get(0);
            int k = 0;
            List<Element> buf = new ArrayList<>();
            for (int j = 0; j < info.children().size(); j++) {
                Element ele = info.children().get(j);
                if (ele.tagName().equals("br")) {
                    for (Element e:buf){
                        System.out.println(e);
                    }
                    System.out.println("================");
                    buf.clear();
                } else buf.add(ele);
            }
            //            try {
//                DownloadUtils.download(coverURL,"pics",title+".jpg");
//                System.out.println("下载成功");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
    }

    @Test
    @SneakyThrows
    public void login(){
        String userName="874748414@qq.com";
        String password="qiu20180622*";
        String ua="122#ACpwEDJ/EExHODpZMEpaEJponDJE7SNEEP7rEJ+/CgMaDCQLpo7iEDpWnDEeK51HpyGZp9hBuDEEJFOPpC76EJponDJL7gNpEPXZpJRgu4Ep+FQLpoGUEJLWn4yP7SQEEyuLpERJ5ORKprZCnaRx9kbQNr14N7kIw/F4GXGw9OnMyE1o+U3GwXGJXwWWb8jKs7qLptkJycMqD+AsxdZUomjcAprvHjuy/C6CO7xb1gye5J+yKTtgI01DqMf2gLAungL4uwXnpLXH8CLUJNbERF3O+ErJzzpangphuy5EEL7tFAqy+4EEyzhDqM3bENLxnSp1uOIEELXZ8oL6JN5EyFfmqW32E5pangL4uO0EDLVr8CpUJ42HyBfDnghbDELanStzvyV6tqogxdtkaAEWc5B5ISF+/HcKhJfSmhWPVcAjrutobYzkRDBhRzQPUHVJt9mxqaC0cJuxvL2ZRKDhaHhjSzyhVgG7VE/hYsyKmpUXQQtsZ7T1TCcvJRHQb/+hAMsvBHLj0/quUWvWsL9rAlxHxP+vtD3+q+0gL9rCuue6LwY8Ia986rV17SWrIkc2UfSvJdD6pOB8WJzBRtS8n+TMkFbSmaLLBvvOV472ng4k+gPHlOWX0BCO0Ob6QU3lftRNRjvUiaYS00b+b2oPEsB/aXPBgce2ul3uy79Y9uhbScETF0XnNtv6g4JW3DsztqzRHuvlpV5Jl4z99IlTtr/d8Uul8Ly/MMJjFLWjbrGRcRh7/4I/a4pn+j1d3UFo/ct0K725o2gAxTnVsea3zembV0YlWA30pk5ZdFSvf2tchrBK9EkqEMH6rtoIcay3dxExTTKCaXQNK8gE34ff9d1SpnzQj+FO8s5J8rcn96gTic602+9Ff9hsHYYzHxgked9bTNWPiLOl/JIUcwceMzDQVP31fGEhgajvbNK85d67QcK2pwYL9Vx7yA5xWv6IGFLK3/E9mdPE2nvtwIB+R7tkO2k1rwssa0ZSFKwVHh8YN8Hn3W3DOxHdGVpMejRDkAefNeNDCYMVoC94q9X94YJQEssRps7xtnfQMXEOxr5zJ1cAD05Q+m++u3NP3vp41lk6vNgueR9FNHiX+TDbUbMXw3fzx8qmFS8E2Y9BaVcB5BBLnMoOpK5X6MpwIMNnHJQbxvG+MzHFW766p+v71MCJvOskA7FIIKtd4V3v8MVKoYzs1YabNuEuNCpK2wCGOXo4X9dIrXraAhDOZtsvQKTffJ9Dm76YmNM4liOVf8uF+5/wrKi+7gFPszEI7cyFi/h3ZY3sbG+Q2NCTDnDq2F4MlEf1Ll9ElrCb18+E6aHSA69MlPsUJwczCJCaHm6mdYM5sYsR7SLJHvOz9h9WNJzDlzB12qB2OiIehvgpzdrTqMv60yXB2EqOoocYxsHfg95LfjMlhAgo3zA7hZDVyRrOLt3S0ca7vltRXUwyRE==";
        String xsrfToken="cfb1f0e1-cd9d-4430-abb8-3d8bc56a13b0";
        String umidToken="TA4453768A680D814669AAC01E3D487193CFEE341340C310A66E14EE3CA";
        Connection connection=Jsoup.connect("https://sellercenter.lazada.com.my/seller/login/submit")
                .followRedirects(true)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36")
                .data("TPL_username", userName)
                .data("TPL_password", password)
                .data("ua",ua)
                .data("X-XSRF-TOKEN",xsrfToken)
                .data("umidToken",umidToken)
                .data("has_captcha","0")
                .ignoreContentType(true);
        connection.method(Connection.Method.POST)
                .header("X-XSRF-TOKEN",xsrfToken)
                .header("X-Requested-With","XMLHttpRequest")
                .header("TE","Trailers")
                .header("Cookie","_uab_collina=158268731420675751971086; c_csrf=cfb1f0e1-cd9d-4430-abb8-3d8bc56a13b0; JSID=19bee0565eb8bffcc20b0d0eeb5e7cb2; CSRFT=f60867e7351e8; TID=fe74a616d51fc37650505ff93ab1b209; t_fv=1582687314328; t_uid=M8r2lOv5SIOOPUGkuleVpqmB6d5l4JvU; t_sid=fBXetARX7x5eQ5sVNxpkFXsiFCxpCypN; utm_channel=NA; XSRF-TOKEN=747a5c16-13bf-416c-b9dc-f6917457f0f5; cna=NKDGFqkpfSoCAXMz7KEEF3zk; _m_h5_tk=c6a0c96168195f43a9353184e16a354a_1582697758359; _m_h5_tk_enc=33a1c8184116e75d33dd22ed115e609b; accountType=email; _ga=GA1.3.131144963.1582688225; _gid=GA1.3.1444754974.1582688225; lzd_cid=bb638316-c78f-49fa-b7f9-b651f4750e38; _bl_uid=vnkFX7O426wr4kpOhjFmimj2U0e0; _lang=en_US; JSESSIONID=node0gwhqc2vy409b14a0todvkfhrs545702.node0");
                Connection.Response response=connection.execute();
        System.out.println(response.body());

    }
}
