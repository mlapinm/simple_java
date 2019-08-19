package com.home.chat.server;

import com.home.network.TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {

    public static void main(String[] args) {
        new ChatServer();
    }

    private ChatServer() {
        System.out.println("Server running ...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection();
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
