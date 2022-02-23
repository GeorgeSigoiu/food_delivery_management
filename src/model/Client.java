package model;

import java.io.Serializable;

public class Client implements Serializable {
    private int clientID;
    private Account account=new Account();
    private String name, email, phone, address;

    public Client(Account account, String name, String email, String phone, String address) {
        this.account = account;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client() {
    }

    public Client(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client(String name, String username, String password) {
        account.setUsername(username);
        account.setPassword(password);
        this.name=name;
    }

    /**
     * Getter pentru numele clientului
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pentru numele clientului
     * @param name String, noul nume al clientului
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pentru email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter pentru email-ul clientului
     * @param email String, noul email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter pentru numarul de telofon al clientului
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter pentru numarul de telofin al clientului
     * @param phone String, noul numar de telefon
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter pentru adresa clientului
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter pentru adresa clientului
     * @param address String, noua adresa
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter pentru contul clientului
     * @return Account
     */
    public Account getAccount() {
        Account newAcc = new Account();
        newAcc.setPassword(account.getPassword());
        newAcc.setUsername(account.getUsername());
        return newAcc;
    }

    /**
     * Setter pentru contul clientului
     * @param acc Account, noul cont
     */
    public void setAccount(Account acc) {
        this.account = acc;
    }

    /**
     * Getter pentru parola de la cont
     * @return String
     */
    public String getPassword() {
        return account.getPassword();
    }

    /**
     * Getter pentru username-ul de la cont
     * @return String
     */
    public String getUsername() {
        return account.getUsername();
    }

    /**
     * Getter pentru id-ul clientului
     * @return int
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Setter pentru id-ul clientului
     * @param clientID int, noul id al clientului
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                "id='"+clientID+"'"+
                '}';
    }
}
