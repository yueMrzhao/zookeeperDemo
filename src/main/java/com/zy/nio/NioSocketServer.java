package com.zy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class NioSocketServer {

    private ServerSocketChannel ssc;
    private Selector selector;

    public NioSocketServer(){

        InetSocketAddress address = new InetSocketAddress("localhost", 2002);
        try {
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(address);
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while(selector.select() > 0){
                for(SelectionKey sk : selector.selectedKeys()){
                    selector.selectedKeys().remove(sk);

                    if(sk.isAcceptable()){
                        SocketChannel channel = ssc.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    }

                    if(sk.isReadable()){
                        Channel channel = sk.channel();
                        if(channel instanceof SocketChannel) {
                            SocketChannel sc = (SocketChannel)channel;
                            ByteBuffer bf = ByteBuffer.allocate(1024);
                            String context = "";
                            try {
                                while (bf.hasRemaining()) {
                                    sc.read(bf);
                                    bf.flip();
                                    context += StandardCharsets.UTF_8.decode(bf);
                                }
                            } catch (Exception e){
                                sk.cancel();
                                if (sk.channel() != null) {
                                    sk.channel().close();
                                }
                            }


                            try {
                                System.out.println("服务端接收来自："+ sc.getRemoteAddress() +" 的消息："+ context);
                                sc.write(StandardCharsets.UTF_8.encode("你好，服务端已经收到消息，正在处理，谢谢！"));
//                            if(context.length() >0) {
//                                for (SelectionKey key : selector.keys()) {
//                                    if(key.channel() instanceof SocketChannel) {
//                                        SocketChannel socketChannel = (SocketChannel) key.channel();
//                                        socketChannel.write(StandardCharsets.UTF_8.encode("你好，服务端已经收到消息，正在处理，谢谢！"));
//                                    }
//                                }
//                            }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NioSocketServer server = new NioSocketServer();
    }
}
