package com.zy.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsynServerHandler implements Runnable{

    public AsynchronousServerSocketChannel serverSocketChannel;
    public CountDownLatch countDownLatch;


    public AsynServerHandler(int port) throws Exception{
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        System.out.println("aioServer has start !");
    }


    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        serverSocketChannel.accept(this, new AcceptHandler());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
