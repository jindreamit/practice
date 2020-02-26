package com.jindreamit.practice.utils.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUtils {
    static public void download(String url,String dirPath,  String fileName) throws Exception {
        new File(dirPath + "/").mkdirs();
        File file = new File(dirPath + "/" + fileName);
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        long all=connection.getContentLengthLong();
        System.out.println(all);
        InputStream in = connection.getInputStream();
        byte[] bytes = new byte[100];
        FileOutputStream out = new FileOutputStream(file);
        int l;
        long current=0;
        while ((l = in.read(bytes)) != -1) {
            current+=l;
//            System.out.println(file.getName()+" 已经下载 " + new DecimalFormat("#.##%").format(current*1.0/all)+" 共 "+ Math.round(all*1.0/1024)+"KB");
            out.write(bytes, 0, l);
        }
        in.close();
        out.flush();
        out.close();
    }
}
