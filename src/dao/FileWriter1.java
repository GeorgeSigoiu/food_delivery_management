package dao;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import model.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FileWriter1 {
    /**
     * Metoda genereaza un fisier nou care reprezinta un bon fiscal pentru comanda efectuata
     * @param client Client, clientul carea a efectuat comanda
     * @param order Order, datele comenzii
     * @param products lista de produse, produsele comandate de client
     */
    public static void generateBill(Client client, Order order, List<MenuItem> products) {
        try {
            File fileOutput = new File("bill/BILL_" + order.getOrderID() + ".txt");
            FileWriter write = new FileWriter(fileOutput);
            PrintWriter pw = new PrintWriter(write);

            String mesaj = "Order no." + order.getOrderID() + "\n\n" +
                    "Date: " + order.getOrderDate() + "\n" +
                    "Name: " + client.getName() + "\n" +
                    "Address: " + client.getAddress() + "\n" +
                    "Email: " + client.getEmail() + "\n" +
                    "Phone: " + client.getPhone() + "\n\n" +
                    "Products:\n";
            StringBuilder sb = new StringBuilder();
            AtomicInteger i = new AtomicInteger(1);
            products.stream()
                    .filter(p -> p instanceof BaseProduct)
                    .forEach(p -> sb.append((i.getAndIncrement()) + ": " + ((BaseProduct) p).getTitle() + "  (price: " + ((BaseProduct) p).computePrice() + ")\n"));
            products.stream()
                    .filter(p->p instanceof CompositeProduct)
                    .forEach(p->{
                        sb.append(i.getAndIncrement()+": "+((CompositeProduct)p).getTitle()+" (total price: "+((CompositeProduct)p).computePrice()+")\n");
                        ((CompositeProduct) p).getProducts().forEach(pr->{
                            sb.append("  -"+pr.getTitle()+" (price: "+pr.computePrice()+")\n");
                        });
                    });
            int price = products.stream().mapToInt(MenuItem::computePrice).sum();
            String finalMesaj = mesaj + sb.toString() + "\n" + "Total price: " + price;
            pw.println(finalMesaj);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda genereaza rapoartele dorite de administrator
     * @param filename String, adresa impreunda cu numele fisierului care sa se creeze
     * @param mesaj String, mesajul care sa fie scris in fisier
     */
    public static void generateRaport(String filename, String mesaj) {
        try {
            File fileOutput = new File(filename);
            FileWriter write = new FileWriter(fileOutput);
            PrintWriter pw = new PrintWriter(write);
            pw.println(mesaj);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
