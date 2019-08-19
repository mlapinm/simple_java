package com.home.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow extends JFrame implements ActionListener {

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
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        log.append(" 555555 " + actionEvent.getModifiers());
    }
}
