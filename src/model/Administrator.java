package model;

import java.io.Serializable;

public class Administrator implements Serializable {
    private Account account=new Account();

    public Administrator() {
    }
    public Administrator(String username, String password) {
        account.setUsername(username);
        account.setPassword(password);
    }
    public Administrator(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        Account acc = new Account();
        acc.setPassword(account.getPassword());
        acc.setUsername(account.getUsername());
        return acc;
    }

    /**
     * Setter pentru contul de administrator
     * @param account Account, noul cont al administratorului
     */
    public void setAccount(Account account) {
        this.account = account;
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
}
