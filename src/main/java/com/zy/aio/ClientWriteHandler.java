package com.zy.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class ClientWriteHandler implements CompletionHandler<Integer,ByteBuffer>{

    private AsynchronousSocketChannel channel;
    private CountDownLatch countDownLatch;

    public ClientWriteHandler(AsynchronousSocketChannel channel, CountDownLatch countDownLatch) {
        this.channel = channel;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        if(attachment.hasRemaining()){
            channel.write(attachment,attachment,this);
        } else {
            ByteBuffer readBff = ByteBuffer.allocate(1024);
            channel.read(readBff, readBff, new ClientReadHandler(countDownLatch));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

        System.out.println("客户端写入消息失败！");
        try {
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
