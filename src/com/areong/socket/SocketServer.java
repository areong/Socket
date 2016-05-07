package com.areong.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class SocketServer {
    private ServerSocket serverSocket;
    private ListeningThread listeningThread;
    private MessageHandler messageHandler;

    public SocketServer(int port, MessageHandler handler) {
        messageHandler = handler;
        try {
            serverSocket = new ServerSocket(port);
            listeningThread = new ListeningThread(this, serverSocket);
            listeningThread.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void setMessageHandler(MessageHandler handler) {
        messageHandler = handler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
    
    /*
     * Ready for use.
     */
    public void close() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                listeningThread.stopRunning();
                listeningThread.suspend();
                listeningThread.stop();

                serverSocket.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}