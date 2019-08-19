package com.home.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port) throws IOException {
        this(eventListener, new Socket(ipAddr, port));


    }

    public TCPConnection(TCPConnectionListener eventListener, Socket socket ) throws IOException {
        this.socket = socket;
        this.eventListener = eventListener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()){
                        String msg = in.readLine();
                        eventListener.onReceiveString(TCPConnection.this, msg);
                    }
                    //                    String msg = in.readLine();

                }catch (IOException e){

                }finally {

                }
            }
        });
        rxThread.start();
    }

    public synchronized void sendString(String value){
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onExeption(TCPConnection.this, e);
            disconnect();
        }finally {
            eventListener.onDisconnect(TCPConnection.this);
        }
    }

    public synchronized void  disconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        }catch (IOException e){
            eventListener.onExeption(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnection: "
                + socket.getInetAddress()
                + " : "
                + socket.getPort();
    }


}