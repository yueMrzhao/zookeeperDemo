package com.zy.socketTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer{

    private ServerSocket socketServer;
    private Socket socket;


    public void start(int port){
        try {
            socketServer = new ServerSocket(port);
            while(true){
                socket = socketServer.accept();
                new Thread(new SocketThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class SocketThread extends Thread{

        private Socket socket;

        public SocketThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {

            try {

                InputStream in = socket.getInputStream();
                DataInputStream reader = new DataInputStream(in);

                String mes = reader.readUTF();
                System.out.println("服务端接收来自 ip为:" + socket.getInetAddress().getHostAddress() + "的消息：" + mes);

                OutputStream out = socket.getOutputStream();
                DataOutputStream writer = new DataOutputStream(out);
                writer.writeUTF("服务端已收到信息！");
                writer.flush();

                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
          /*启动服务端*/
        SocketServer server = new SocketServer();
        server.start(2002);

    }
}
