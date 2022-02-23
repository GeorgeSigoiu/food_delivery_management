package model;

import java.io.Serializable;

public class Employee implements Serializable {
    private Account account = new Account();

    public Employee() {
    }

    public Employee(Account account) {
        this.account = account;
    }

    public Employee(String username, String password) {
        account.setPassword(password);
        account.setUsername(username);
    }

    /**
     * Getter pentru username-ul din account
     * @return String
     * @see Account
     */
    public String getUsername() {
        return account.getUsername();
    }

    /**
     * Getter pentru parola din account
     * @return String
     * @see Account
     */
    public String getPassword() {
        return account.getPassword();
    }

    /**
     * Getter pentru account
     * @return Account
     * @see Account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Setter pentru account
     * @param account Account, noua valoarea atributului accopunt
     * @see Account
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
