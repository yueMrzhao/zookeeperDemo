package com.zy.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,AsynServerHandler>{
    @Override
    public void completed(AsynchronousSocketChannel result, AsynServerHandler attachment) {

        Server.linkCount ++;
        System.out.println("客户端连接数增加："+ Server.linkCount);
        attachment.serverSocketChannel.accept(attachment,this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer,byteBuffer,new ReadHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsynServerHandler attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }
}
