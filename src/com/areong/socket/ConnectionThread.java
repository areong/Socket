package com.areong.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ConnectionThread extends Thread {
    private Socket socket;
    private SocketServer socketServer;
    private Connection connection;
    private boolean isRunning;

    public ConnectionThread(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
        connection = new Connection(socket);
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            // Check whether the socket is closed.
            if (socket.isClosed()) {
                isRunning = false;
                break;
            }
            
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(
                                            socket.getInputStream()));
                String rawMessage = reader.readLine();
                String messageFlag = rawMessage.substring(0, 1);
                String message = rawMessage.substring(1);

                // Check the message flag.
                switch (messageFlag) {
                case MessageFlag.pureMessage:
                    // Handle the message.
                    if (message != null) {
                        socketServer.getMessageHandler().onReceive(connection, message);
                    }
                    break;
                case MessageFlag.connectionClosed:
                    stopRunning();
                    break;
                default:
                    break;
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
    
    public void stopRunning() {
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}