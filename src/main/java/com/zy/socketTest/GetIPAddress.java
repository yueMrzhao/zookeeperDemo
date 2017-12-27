package com.zy.socketTest;

import org.apache.commons.net.SocketClient;

import java.io.*;
import java.net.*;

public class GetIPAddress {

    public static void main(String[] args) {
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getByName("www.baidu.com");
            System.out.println("inetAddress.getHostAddress()  = " + inetAddress.getHostAddress());
//            System.out.println("inetAddress.getCanonicalHostName() = " + inetAddress.getCanonicalHostName());
//            printPortOnUsing();
//            printLocalInfo();
//            getRemoteFileSize();
            getWwebContext();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /*判断端口是否被使用*/
    public static void printPortOnUsing() {


        for (int i = 0; i < 10; i++) {
            try {
//            System.out.println("查看端口："+ i);
            Socket socket = new Socket("localhost", 49427);
            System.out.println("端口：" + i+ "已被占用！");
            } catch (IOException e) {
            }
        }

    }

    /*获取本机ip*/
    public static void printLocalInfo(){
        try {
            InetAddress localip = InetAddress.getLocalHost();
            System.out.println(localip.getHostName() +" = " + localip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /*获取远程文件的大小，并储存到本地*/
    public static void getRemoteFileSize(){
        try {
            URL url = new URL("http://www.runoob.com/wp-content/themes/runoob/assets/img/newlogo.png");
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
            System.out.println("urlConnectiongetContentLength() = " + urlConnection.getContentLength());
            System.out.println(String.format("文件最后修改时间为：%tF",urlConnection.getLastModified()));
            File file = new File("newlogo.png");
            System.out.println("file = " + file.getAbsolutePath());
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            byte[] temp = new byte[1024];
//            while (in.read(temp)!=-1){
//                fileOutputStream.write(temp);
//            }

            in.close();
//            fileOutputStream.flush();
//            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public static void getWwebContext(){
        String weburl = "http://www.runoob.com/java/java-examples.html";
        try {
            URL url = new URL(weburl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result ;
            while((result = reader.readLine()) != null){
                System.out.println("从网页获取的内容为：" + result);
            }
            conn.getInputStream().close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        }
    }
}
