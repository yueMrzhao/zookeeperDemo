package com.zy.aio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class ClientReadHandler implements CompletionHandler<Integer,ByteBuffer>{

    private CountDownLatch countDownLatch;

    public ClientReadHandler(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);

        try {
            String mes = new String(message,"utf-8");
            System.out.println("客户端接收的信息为："+ mes);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.out.println("客户端读取消息失败.....");
        try {
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
