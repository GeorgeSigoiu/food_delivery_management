package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegisteredUsers {
    /**
     * lista in care sunt stocati clientii
     */
    private static List<Client> clients = new ArrayList<>();
    /**
     * lista in care sunt stocati administartorii
     */
    private static List<Administrator> admins = new ArrayList<>();
    /**
     * lista in care sunt stocati angajatii
     */
    private static List<Employee> employees = new ArrayList<>();

    /**
     * Adauga in lista clientilor un client nou
     * @param c Client, clientul care va fi adaugat
     * @see Client
     */
    public static void addUser(Client c) {
        int id = clients.size() + 1;
        c.setClientID(id);
        clients.add(c);
    }

    /**
     * Adauga in lista administratorilor un administrator nou
     * @param a Administrator, administratorului care va fi adaugat
     * @see Administrator
     */
    public static void addUser(Administrator a) {
        admins.add(a);
    }

    /**
     * Adauga in lista angajatilor un angajat nou
     * @param e Employee, angajatul care va fi adaugat
     * @see Employee
     */
    public static void addUser(Employee e) {
        employees.add(e);
    }

    /**
     * Getter pentru clienti
     * @return lista de clienti
     */
    public static List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    /**
     * Getter pentru administratori
     * @return lista de administratori
     */
    public static List<Administrator> getAdmins() {
        return new ArrayList<>(admins);
    }

    /**
     * Getter pentru angajati
     * @return lista de angajati
     */
    public static List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }
}
