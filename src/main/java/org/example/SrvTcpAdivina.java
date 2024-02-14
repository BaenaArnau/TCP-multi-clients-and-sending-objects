package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SrvTcpAdivina {
    int port;

    public SrvTcpAdivina(int port) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) { // Esperar conexión del cliente y lanzar hilo
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // Lanzar hilo para manejar la comunicación con el cliente
                ThreadServidorAdivina threadServidor = new ThreadServidorAdivina(clientSocket);
                Thread clientThread = new Thread(threadServidor);
                clientThread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(SrvTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(SrvTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        SrvTcpAdivina srv = new SrvTcpAdivina(5558);
        srv.listen();
    }
}