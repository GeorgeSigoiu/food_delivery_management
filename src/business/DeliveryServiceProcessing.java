package business;

import model.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface DeliveryServiceProcessing {
    /**
     * Importa produsele in memorie dintr-un fisier specificat
     * @param pathFile String, fisierul din care se importa produsele
     */
    public void importProducts(String pathFile);

    /**
     * Adaugarea unui produs in memorie
     * @param p MenuItem, produsul care trebuie adaugat
     */
    public void addNewProduct(MenuItem p);

    /**
     * Stergerea unui produs din memorie
     * @param p MenuItem, produsul care trebuie sters
     */
    public void deleteProduct(MenuItem p);

    /**
     * Modificarea produselor din memorie
     * @param newProducts lista de produse simple, noile valori ale produselor
     */
    public void editProduct(List<BaseProduct> newProducts);

    /**
     * Genereare unui raport privind produsele comandate intr-un interval orar
     * @param startHour int, ora de incepere
     * @param endHour int, ora de terminare
     */
    public void generateTimeIntervalOrderReport(int startHour, int endHour);

    /**
     * Generarea unui raport cu privire clientii care au comandat de minim times ori, comenzi care au valoare mai mare
     * decat minValue
     * @param times int, numarul minim de comenzi efectuate de catre un client
     * @param minValue int, valoarea minima a unei comenzi
     */
    public void generateOrderReport(int times, int minValue);

    /**
     * Generearea unui raport privind produsele comandate de minim times ori
     * @param times int, numarul minim de cate ori un produs a fost comandat
     */
    public void generateOrderReport(int times);

    /**
     * Generarea unui raport privind produsele comandate intr-o anumita zi
     * @param dt String, ziua in care se doreste raportul
     */
    public void generateOrderReport(String dt);

    /**
     * Crearea unei comenzi efectuate de client
     *
     * @param client   Client, clientul care efectueaza comanda
     * @param products lista de produse, produsele din comanda
     */
    public void createOrder(Client client, List<MenuItem> products);
}
