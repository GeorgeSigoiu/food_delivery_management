package model;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter pentru username
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter pentru username
     * @param username String, noul username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter pentru parola
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter pentru parola
     * @param password String, noua parola
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
