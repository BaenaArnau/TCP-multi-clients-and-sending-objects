package org.example;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class ThreadServidorAdivina implements Runnable {
    private Socket clientSocket;

    public ThreadServidorAdivina(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            // Recibir el objeto Llista del cliente
            Llista lista = (Llista) inStream.readObject();
            System.out.println("Received list from client: " + lista.getNom() + " " + lista.getNumberList());

            // Ordenar la lista
            List<Integer> numbers = lista.getNumberList();
            Collections.sort(numbers);

            // Eliminar duplicados manteniendo el orden
            LinkedHashSet<Integer> uniqueNumbers = new LinkedHashSet<>(numbers);
            numbers = new ArrayList<>(uniqueNumbers);

            // Actualizar la lista en 'lista'
            lista.setNumberList(numbers);


            // Enviar la lista modificada de vuelta al cliente
            outStream.writeObject(lista);
            outStream.flush();
            System.out.println("Sent sorted and deduplicated list to client: " + lista.getNom() + " " + lista.getNumberList());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ThreadServidorAdivina.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidorAdivina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}