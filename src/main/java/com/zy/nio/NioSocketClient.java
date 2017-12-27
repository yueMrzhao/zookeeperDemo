package com.zy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NioSocketClient {

    private SocketChannel sc;
    private Selector selector;

    public NioSocketClient(){
        InetSocketAddress isa = new InetSocketAddress("localhost", 2002);
        try {
            selector = Selector.open();
            sc = SocketChannel.open(isa);
            sc.configureBlocking(false);
            sc.register(selector,SelectionKey.OP_READ);

            new ClientThread().start();
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                sc.write(StandardCharsets.UTF_8.encode(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ClientThread extends Thread {
        @Override
        public void run() {

            try {
                while (selector.select() > 0) {
                    for (SelectionKey sk : selector.selectedKeys()) {

                        selector.selectedKeys().remove(sk);
                        if (sk.isReadable()) {
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer bf = ByteBuffer.allocate(1024);
                            String result = "";
                            while (bf.hasRemaining()) {
                                sc.read(bf);
                                bf.flip();
                                result += StandardCharsets.UTF_8.decode(bf);
                            }
                            System.out.println("客户端接收到消息：" + result);

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NioSocketClient client = new NioSocketClient();
    }


}
