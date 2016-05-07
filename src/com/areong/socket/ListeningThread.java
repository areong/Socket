package com.areong.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class ListeningThread extends Thread {
    private SocketServer socketServer;
    private ServerSocket serverSocket;
    private Vector<ConnectionThread> connThreads;
    private boolean isRunning;

    public ListeningThread(SocketServer socketServer, ServerSocket serverSocket) {
        this.socketServer = socketServer;
        this.serverSocket = serverSocket;
        this.connThreads = new Vector<ConnectionThread>();
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            if (serverSocket.isClosed()) {
                isRunning = false;
                break;
            }
            
            try {
                Socket socket;
                socket = serverSocket.accept();
                ConnectionThread conn = new ConnectionThread(socket, socketServer);
                connThreads.addElement(conn);
                conn.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void stopRunning() {
        for (int i = 0; i < connThreads.size(); i++)
            connThreads.elementAt(i).stopRunning();
        isRunning = false;
    }
} 