package com.jindreamit.practice.socket.chat.message;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义协议
 * 读取前 21 个字节为消息长度(消息最长为 10^20个字节,即 1M)
 * 客户端和服务端都使用UTF8编码
 */
public class Message {

    private Message(){
    }

    private Message(String msg,int contentLength,Charset charset) {
        this.contentLength = contentLength;
        this.msg = msg;
        this.charset=charset;
    }

    private int contentLength;

    private String msg;

    private Charset charset;

    static private class Builder {

        private Builder() {

        }

        private int contentLength;

        private String msg;

        private Charset charset;

        public Builder message(String message){
            this.msg=message;
            return this;
        }

        public Builder contentLength(Charset charset){
            this.charset=charset;
            return this;
        }

        public Message build(){
            int l=this.msg.getBytes(this.charset).length;
            this.contentLength=("Content-Length: "+l).getBytes(this.charset).length+l;
            this.msg="Content-Length: "+l+this.msg;
            return new Message(this.msg,this.contentLength,this.charset);
        }

    }

    static public Builder builder = new Builder();

    public byte[] getMessageBytes(){
        return this.msg.getBytes(StandardCharsets.UTF_8);
    }

}
