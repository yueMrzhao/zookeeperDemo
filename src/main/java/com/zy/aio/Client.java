package com.zy.aio;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        ClientHandler client = null;
        try {
            client = new ClientHandler("localhost", 2212);
            new Thread(client, "aioClient").start();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                client.sendMsg(scanner.nextLine());
            }
        } finally {
            try {
                client.channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
