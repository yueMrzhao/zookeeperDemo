package com.zy.aio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class ClientHandler implements Runnable,CompletionHandler<Void, ClientHandler> {

    public AsynchronousSocketChannel channel;
    private String host;
    private int port;
    private CountDownLatch countDownLatch;

    public ClientHandler(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, ClientHandler attachment) {
        System.out.println("客户端成功连接到服务器...");
    }

    @Override
    public void failed(Throwable exc, ClientHandler attachment) {
        System.err.println("连接服务器失败...");
        exc.printStackTrace();
        try {
            channel.close();
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        channel.connect(new InetSocketAddress(host,port),this,this);

        try {
            countDownLatch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向服务器发送消息
    public void sendMsg(String msg){
        byte[] req = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        //异步写
        channel.write(writeBuffer, writeBuffer,new ClientWriteHandler(channel, countDownLatch));
    }
}
