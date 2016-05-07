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
                String message = reader.readLine();
                if (message != null)
                    socketServer.getMessageHandler().onReceive(connection, message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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