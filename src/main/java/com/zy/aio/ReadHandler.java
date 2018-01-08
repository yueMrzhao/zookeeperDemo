package com.zy.aio;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer,ByteBuffer>{

    private AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {

        if(!channel.isOpen()) return;
        attachment.flip();
        if(attachment.remaining()==0) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);

        try {
            String mes = new String(message,"utf-8");
            System.out.println("服务端接收的信息为："+ mes);

            String calcResult = "";
            try {
                calcResult = Calculator.cal(mes).toString();
            } catch (ScriptException e){
                calcResult = "计算错误" + calcResult;
            }

            doWrite(calcResult);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void doWrite(String mes){
        ByteBuffer writeBuff = ByteBuffer.allocate(1024);
        byte[] bytes = mes.getBytes();
        writeBuff.put(bytes);
        writeBuff.flip();

        channel.write(writeBuff, writeBuff, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining()){
                    channel.write(attachment,attachment,this);
                } else {
                    ByteBuffer readBff = ByteBuffer.allocate(1024);
                    channel.read(readBff, readBff, new ReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
