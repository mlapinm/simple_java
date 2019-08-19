package com.home.chat.client;

import com.home.network.TCPConnection;
import com.home.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    public static final String IP_ADDR = "127.0.0.1";
    public static final int PORT = 8189;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final  JTextArea log = new JTextArea();

    private final JTextField fieldNikName = new JTextField("Mike");
    private final JTextField fieldInput = new JTextField();
    private final JButton button = new JButton();

    private TCPConnection connection;

    private ClientWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);
        log.setText("123");
        add(log, BorderLayout.CENTER);
        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);
        button.setText("b1");
        button.addActionListener(this);
        add(button, BorderLayout.EAST);
        add(fieldNikName, BorderLayout.NORTH);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String msg = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString(fieldNikName.getText()
                + ": "
                + msg);

    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close...");
    }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\r\n");
                log.setCaretPosition(log.getDocument().getLength());

            }
        });

    }

}
