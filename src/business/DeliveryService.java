package business;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import dao.FileWriter1;
import dao.ReadExcel;
import dao.Serializator;
import model.Client;
import model.RegisteredUsers;
import presentation.ReportWindow;

/**
 * Clasa care implementeaza functiile administratorului si ale clientului
 */
public class DeliveryService extends Observable implements DeliveryServiceProcessing {
    private static Map<Integer, List<MenuItem>> products = new HashMap<>();
    private static Map<Order, List<MenuItem>> orders = new HashMap<>();
    private static int totalOrders = 0;

    /**
     * Getter pentru numarul total de comenzi efectuate in aplicatie
     * @return int
     */
    public static int getTotalOrders() {
        return totalOrders;
    }

    /**
     * Setter pentru numarul total de comenzi efectuate in aplicatie
     * @param totalOrders int, numarul de comenzi efectuate
     */
    public static void setTotalOrders(int totalOrders) {
        DeliveryService.totalOrders = totalOrders;
    }

    /**
     * Getter pentru comenzile efectuate de catre clienti
     * @return mapare lista de produse la comenzi
     */
    public static Map<Order, List<MenuItem>> getOrders() {
        return orders;
    }

    /**
     * Setter pentru comenzile efectuate de catre clienti
     * @param orders mapare lista de produse la comenzi, comenzile propriu zise
     */
    public static void setOrders(Map<Order, List<MenuItem>> orders) {
        DeliveryService.orders = orders;
    }

    /**
     * Adaugare a unui produs in memorie
     * @param i int, 1-BaseProduct, 2-CompositeProduct
     * @param p MenuItem, produsul care trebuie adaugat
     */
    public static void addProduct(int i, MenuItem p) {
        products.computeIfAbsent(i, k -> new ArrayList<>());
        assert p!=null;
        products.get(i).add(p);
    }

    /**
     * Setter pentru produsele din memorie
     * @param i int, 1-BaseProduct, 2-CompositeProduct
     * @param produse lista de produse, produsele care trebuie adaugate
     */
    public static void setProducts(int i, List<MenuItem> produse) {
        List<MenuItem> prod = new ArrayList<>(produse);
        products.put(i, prod);
    }

    /**
     * Getter pentru produsele compuse
     * @return lista de produse compuse
     */
    public static List<CompositeProduct> getCompositeProducts() {
        List<MenuItem> items = products.get(2);
        List<CompositeProduct> prod = new ArrayList<>();
        if (items != null)
            items.forEach(m -> prod.add((CompositeProduct) m));
        return prod;
    }

    /**
     * Getter pentru produsele simple
     * @return lista de produse simple
     */
    public static List<BaseProduct> getBaseProducts() {
        List<MenuItem> items = products.get(1);
        List<BaseProduct> prod = new ArrayList<>();
        if (items != null)
            items.forEach(m -> prod.add((BaseProduct) m));
        return prod;
    }
    /**
     * Importa produsele in memorie dintr-un fisier specificat
     * @param pathFile String, fisierul din care se importa produsele
     */
    @Override
    public void importProducts(String pathFile) {
        List<BaseProduct> products = ReadExcel.read(pathFile);
        setProducts(1, new ArrayList<>(products));
        setProducts(2, new ArrayList<>());
        Serializator.serializeBaseProducts();
        Serializator.serializeCompositeProducts();
    }
    /**
     * Adaugarea unui produs in memorie
     * @param p MenuItem, produsul care trebuie adaugat
     */
    @Override
    public void addNewProduct(MenuItem p) {
        if (p instanceof BaseProduct) {
            addProduct(1, p);
            Serializator.serializeBaseProducts();
        }
        if (p instanceof CompositeProduct) {
            addProduct(2, p);
            Serializator.serializeCompositeProducts();
        }
    }
    /**
     * Stergerea unui produs din memorie
     * @param p MenuItem, produsul care trebuie sters
     */
    @Override
    public void deleteProduct(MenuItem p) {
        if (p instanceof BaseProduct) {
            List<BaseProduct> products = DeliveryService.getBaseProducts();
            String name = ((BaseProduct) p).getTitle();
            int poz = 0;
            for (BaseProduct pr : products) {
                if (pr.getTitle().equals(name)) break;
                poz++;
            }
            products.remove(poz);
            setProducts(1, new ArrayList<>(products));
            Serializator.serializeBaseProducts();
        }
        if (p instanceof CompositeProduct) {
            List<CompositeProduct> products = getCompositeProducts();
            String name = ((CompositeProduct) p).getTitle();
            int poz = 0;
            for (CompositeProduct pr : products) {
                if (pr.getTitle().equals(name)) break;
                poz++;
            }
            products.remove(poz);
            setProducts(2, new ArrayList<>(products));
            Serializator.serializeCompositeProducts();
        }
    }
    /**
     * Modificarea produselor din memorie
     * @param newProducts lista de produse simple, noile valori ale produselor
     */
    @Override
    public void editProduct(List<BaseProduct> newProducts) {
        setProducts(1, new ArrayList<>());
        List<BaseProduct> products = new ArrayList<>();
        for (MenuItem mi : newProducts) {
            BaseProduct p = (BaseProduct) mi;
            products.add(p);
        }
        setProducts(1, new ArrayList<>(products));
        Serializator.serializeBaseProducts();
    }
    /**
     * Genereare unui raport privind produsele comandate intr-un interval orar
     * @param startHour int, ora de incepere
     * @param endHour int, ora de terminare
     */
    @Override
    public void generateTimeIntervalOrderReport(int startHour, int endHour) {
        String mesaj = "\n  Orders generated between " + startHour + " - " + endHour + ": ";
        Set<Order> orders = DeliveryService.getOrders().keySet();
        int number = (int) orders.stream()
                .filter(order -> startHour <= order.getDate().getHour() && order.getDate().getHour() <= endHour)
                .count();
        mesaj += number + "";
        new ReportWindow(mesaj);
        FileWriter1.generateRaport("generated_raports/time_interval_report.txt", mesaj);
    }
    /**
     * Generarea unui raport cu privire clientii care au comandat de minim times ori, comenzi care au valoare mai mare
     * decat minValue
     * @param times int, numarul minim de comenzi efectuate de catre un client
     * @param minValue int, valoarea minima a unei comenzi
     */
    @Override
    public void generateOrderReport(int times, int minValue) {
        Set<Order> orders = DeliveryService.getOrders().keySet();
        orders = orders.stream().filter(order -> getOrderValue(order) >= minValue).collect(Collectors.toSet());
        List<Integer> clientsID = new ArrayList<>();
        List<Integer> finalClientsID = clientsID;
        orders.forEach(order -> {
            if (!finalClientsID.contains(order.getClientID()))
                finalClientsID.add(order.getClientID());
        });
        clientsID = clientsID.stream().sorted().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        Set<Order> finalOrders = orders;
        AtomicInteger i = new AtomicInteger(1);
        clientsID.forEach(id -> {
            if (finalOrders.stream().filter(order -> order.getClientID() == id).count() >= times)
                sb.append("  ").append(i.getAndIncrement()).append(". ").append(getClientName(id)).append(" (id = ").append(id).append(")\n");
        });
        String mesaj = "\n  Clients that have ordered more than " + times + " times and the value of the order was higher than " + minValue + ":\n" + sb.toString();
        FileWriter1.generateRaport("generated_raports/clients_order_minValue_ntimes.txt", mesaj);
        new ReportWindow(mesaj);
    }

    /**
     * Determinarea numelui unui client
     * @param id int, id-ul clientului pentru care se cauta numele
     * @return String
     */
    private String getClientName(int id) {
        return RegisteredUsers.getClients().stream().filter(c -> c.getClientID() == id).collect(Collectors.toList()).get(0).getName();
    }

    /**
     * Determinare pret total al unei comenzi
     * @param order Order, comanda pentru care se calculeaza pretul
     * @return int
     */
    private int getOrderValue(Order order) {
        return DeliveryService.getOrders().get(order).stream().mapToInt(MenuItem::computePrice).sum();
    }
    /**
     * Generearea unui raport privind produsele comandate de minim times ori
     * @param times int, numarul minim de cate ori un produs a fost comandat
     */
    @Override
    public void generateOrderReport(int times) {
        List<MenuItem> products = new ArrayList<>();
        Set<Order> orders = DeliveryService.getOrders().keySet();
        Map<Order, List<MenuItem>> allOrders = DeliveryService.getOrders();
        List<List<MenuItem>> allProducts = new ArrayList<>();
        orders.forEach(key -> allProducts.add(allOrders.get(key)));
        allProducts.forEach(products::addAll);
        List<MenuItem> finalProducts = new ArrayList<>();
        products.stream()
                .filter(p -> p instanceof BaseProduct)
                .forEach(finalProducts::add);
        products.stream()
                .filter(p -> p instanceof CompositeProduct)
                .forEach(cp -> finalProducts.addAll(((CompositeProduct) cp).getProducts()));
        List<String> title = new ArrayList<>();
        List<String> finalTitle = title;
        finalProducts.forEach(p -> {
            if (!(finalTitle.contains(((BaseProduct) p).getTitle())))
                finalTitle.add(((BaseProduct) p).getTitle());
        });
        title = title.stream().sorted((n1, n2) -> n1.toLowerCase().compareTo(n2.toLowerCase())).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        AtomicInteger i = new AtomicInteger(1);
        title.forEach(name -> {
            if (finalProducts.stream().filter(p -> ((BaseProduct) p).getTitle().equals(name)).count() >= times) {
                sb.append("  ").append(i.getAndIncrement()).append(". ").append(name).append("\n");
            }
        });
        String mesaj = "\n  The products ordered more than " + times + " times so far:\n" + sb.toString();
        FileWriter1.generateRaport("generated_raports/products_ordered_n_times.txt", mesaj);
        new ReportWindow(mesaj);
    }
    /**
     * Generarea unui raport privind produsele comandate intr-o anumita zi
     * @param dt String, ziua in care se doreste raportul
     */
    @Override
    public void generateOrderReport(String dt) {
        Set<Order> orders = DeliveryService.getOrders().keySet().stream().filter(order -> order.getOrderDate().equals(dt)).collect(Collectors.toSet());
        List<List<MenuItem>> productLists = new ArrayList<>();
        orders.forEach(order -> productLists.add(DeliveryService.getOrders().get(order)));
        List<MenuItem> allProducts = new ArrayList<>();
        productLists.forEach(allProducts::addAll);
        List<MenuItem> products = allProducts.stream().filter(p -> p instanceof BaseProduct).collect(Collectors.toList());
        allProducts.stream().filter(p -> p instanceof CompositeProduct)
                .forEach(cp -> products.addAll(((CompositeProduct) cp).getProducts()));
        List<String>title=new ArrayList<>();
        List<String> finalTitle = title;
        products.forEach(p->{
            if(!finalTitle.contains(((BaseProduct)p).getTitle()))
                finalTitle.add(((BaseProduct)p).getTitle());
        });
        title=title.stream().sorted((n1,n2)->n1.toLowerCase().compareTo(n2.toLowerCase())).collect(Collectors.toList());
        StringBuilder sb=new StringBuilder("\n  the products ordered in "+dt+":\n");
        AtomicInteger i= new AtomicInteger(1);
        title.forEach(name->{
            long count=products.stream().filter(p->((BaseProduct)p).getTitle().equals(name)).count();
            sb.append("  ").append(i.getAndIncrement()).append(". ").append(name).append(" --> ").append(count).append("\n");
        });
        String mesaj=sb.toString();
        FileWriter1.generateRaport("generated_raports/products_ordered_specified_day.txt",mesaj);
        new ReportWindow(mesaj);
    }
    /**
     * Crearea unei comenzi efectuate de client
     *
     * @param client   Client, clientul care efectueaza comanda
     * @param products lista de produse, produsele din comanda
     */
    @Override
    public void createOrder(Client client, List<MenuItem> products) {
        Order order = new Order(totalOrders + 1, client.getClientID(), LocalDateTime.now());
        totalOrders++;
        orders.put(order, products);
        FileWriter1.generateBill(client, order, products);
        Serializator.serializeOrders();
        setChanged();
        notifyObservers(order.getOrderID());
    }
    private static Observer ob;
    public static void addObs(Observer o){
        ob=o;
    }
    @Override
    public void notifyObservers(Object o) {
        assert (o instanceof Integer);
        ob.update(this,o);
    }
}
