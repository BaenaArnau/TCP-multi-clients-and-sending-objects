package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTcpAdivina {
    String hostname;
    int port;

    public ClientTcpAdivina(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Crear y enviar objeto Llista al servidor
            Llista lista = createList();
            out.writeObject(lista);
            out.flush();
            System.out.println("Sent list to server: " + lista.getNom() + " " + lista.getNumberList());

            // Recibir el objeto modificado del servidor
            Llista modifiedList = (Llista) in.readObject();
            System.out.println("Received modified list from server: " + modifiedList.getNom() + " " + modifiedList.getNumberList());

        } catch (UnknownHostException ex) {
            System.out.println("Error de conexión. No existe el host: " + ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error de conexión: " + ex.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Llista createList() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el nombre de la lista: ");
        String nombre = scanner.nextLine();
        List<Integer> numeros = new ArrayList<>();
        System.out.println("Introduce los números (introduce un número negativo para terminar): ");
        int num;
        while ((num = scanner.nextInt()) >= 0) {
            numeros.add(num);
        }
        return new Llista(nombre, numeros);
    }

    public static void main(String[] args) {
        ClientTcpAdivina clientTcp = new ClientTcpAdivina("localhost", 5558);
        clientTcp.run();
    }
}
