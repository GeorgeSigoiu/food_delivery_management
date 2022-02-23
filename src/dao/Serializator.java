package dao;

import business.*;
import model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Serializator {
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    /**
     * Serializarea comenzilor
     */
    public static void serializeOrders() {
        openFileForWrite("serializator_files/orders.txt");
        try {
            out.writeInt(DeliveryService.getTotalOrders());
            out.writeObject(DeliveryService.getOrders());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFileForWrite();
    }

    /**
     * Serializaera produselor compuse
     */
    public static void serializeCompositeProducts() {
        openFileForWrite("serializator_files/compositeProducts.txt");
        try {
            out.writeInt(DeliveryService.getCompositeProducts().size());
            for (CompositeProduct cp : DeliveryService.getCompositeProducts()) {
                out.writeObject(cp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFileForWrite();
    }

    /**
     * Serializarea produselor simple
     */
    public static void serializeBaseProducts() {
        openFileForWrite("serializator_files/baseProducts.txt");
        try {
            out.writeInt(DeliveryService.getBaseProducts().size());
            for (BaseProduct p : DeliveryService.getBaseProducts()) {
                out.writeObject(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFileForWrite();
    }

    /**
     * Serializarea clientilor
     */
    public static void serializeClients() {
        Serializator.openFileForWrite("serializator_files/clients.txt");
        try {
            out.writeInt(RegisteredUsers.getClients().size());
            for (Client c : RegisteredUsers.getClients()) {
                out.writeObject(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Serializator.closeFileForWrite();
    }

    /**
     * Serializarea administratorilor
     */
    public static void serializeAdmins() {
        openFileForWrite("serializator_files/admins.txt");
        try {
            out.writeInt(RegisteredUsers.getAdmins().size());
            for (Administrator a : RegisteredUsers.getAdmins()) {
                out.writeObject(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFileForWrite();
    }

    /**
     * Serializarea angajatilor
     */
    public static void serializeEmployees() {
        openFileForWrite("serializator_files/employees.txt");
        try {
            out.writeInt(RegisteredUsers.getEmployees().size());
            for (Employee e : RegisteredUsers.getEmployees()) {
                out.writeObject(e);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        closeFileForWrite();
    }

    /**
     * Deserializarea produselor compuse
     */
    public static void deserializeCompositeProducts() {
        openFileForRead("serializator_files/compositeProducts.txt");
        if (in == null) return;
        try {
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                CompositeProduct readProduct = (CompositeProduct) in.readObject();
                DeliveryService.addProduct(2, readProduct);
            }
        } catch (IOException | ClassNotFoundException e) {

        }
        closeFileForRead();
    }

    /**
     * Deserializarea comenzilor
     */
    public static void deserializeOrders() {
        openFileForRead("serializator_files/orders.txt");
        if (in == null) return;
        try {
            int n = in.readInt();
            DeliveryService.setTotalOrders(n);
            DeliveryService.setOrders((Map<Order, List<MenuItem>>) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeFileForRead();
    }

    /**
     * Deserializarea produselor simple
     */
    public static void deserializeBaseProducts() {
        openFileForRead("serializator_files/baseProducts.txt");
        if (in == null) return;
        try {
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                BaseProduct readProduct = (BaseProduct) in.readObject();
                DeliveryService.addProduct(1, readProduct);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeFileForRead();
    }

    /**
     * Deserializarea clientilor
     */
    public static void deserializeClients() {
        openFileForRead("serializator_files/clients.txt");
        if (in == null) return;
        try {
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                Client readClient = (Client) in.readObject();
                RegisteredUsers.addUser(readClient);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeFileForRead();
    }
    /**
     * Deserializarea administratorilor
     */
    public static void deserializeAdmins() {
        openFileForRead("serializator_files/admins.txt");
        if (in == null) return;
        try {
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                Administrator readAdmin = (Administrator) in.readObject();
                RegisteredUsers.addUser(readAdmin);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeFileForRead();
    }

    /**
     * Deserializarea angajatilor
     */
    public static void deserializeEmployees() {
        openFileForRead("serializator_files/employees.txt");
        if (in == null) return;
        try {
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                Employee readEmployee = (Employee) in.readObject();
                RegisteredUsers.addUser(readEmployee);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeFileForRead();
    }

    /**
     * Deschiderea fisierului pentru deserializare
     * @param filename String, fisierul din care se vor citi datele
     */
    public static void openFileForRead(String filename) {
        try {
            fileIn = new FileInputStream(filename);
            in = new ObjectInputStream(fileIn);
        } catch (EOFException e1) {
            System.out.println(filename + " is empty");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deschiderea fisierului pentru serializare
     * @param filename String, fisierul in care se vor scrie datele
     */
    public static void openFileForWrite(String filename) {
        try {
            fileOut = new FileOutputStream(filename);
            out = new ObjectOutputStream(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inchiderea fisierului pentru citire
     */
    public static void closeFileForRead() {
        if (in == null) return;
        try {
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Inchiderea fisierului pentru scriere
     */
    public static void closeFileForWrite() {
        try {
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
