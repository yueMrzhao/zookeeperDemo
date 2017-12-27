package com.zy.socketTest;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketClient {

    private Socket socket;
    private SocketAddress socketAddress;
    private AtomicBoolean isStart = new AtomicBoolean(false);

    public SocketClient(String ip, int port){
        try {
            if(isStart.compareAndSet(false,true)) {
                socket = new Socket(ip, port);
                socket.setSoTimeout(10000);
                socketAddress = socket.getRemoteSocketAddress();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("找不到对应的ip");
        }
    }

    public static void sendMes(String mes){
        try {
            /**
             * 每次发送消息，从新连接，否则，由于inputStream.read()的机制，会一直堵塞，导致下次
             * 发送消息的时候线程堵塞，发不出
             */
            Socket socket = new Socket("localhost", 2002);
            OutputStream out = socket.getOutputStream();
            DataOutputStream writer = new DataOutputStream(out);
            writer.writeUTF(mes);
            InputStream in = socket.getInputStream();
            DataInputStream reader = new DataInputStream(in);

            System.out.println("客户端接收的消息为：" + reader.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        synchronized (socket){
            if(isStart.compareAndSet(true,false)){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
         /*客户端发送*/

        while(true) {

            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                String mes = scanner.nextLine();
                SocketClient.sendMes(mes);
            }

        }
    }

}
