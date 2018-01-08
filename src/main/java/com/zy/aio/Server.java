package com.zy.aio;

public class Server {

    public static int linkCount;

    public static void main(String[] args) {

        try {
            AsynServerHandler asynServerHandler = new AsynServerHandler(2212);
            new Thread(asynServerHandler,"aioServer").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
