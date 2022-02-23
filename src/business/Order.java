package business;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clasa care contine informatiile unei comenzi
 */
public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private LocalDateTime orderDate;

    public Order(int orderID, int clientID, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    /**
     * Getter pentru order id
     * @return int
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Getter pentru data comenzii in format dd/mm/yyyy
     * @return String
     */
    public String getOrderDate() {
        return orderDate.getDayOfMonth() + "/" + (orderDate.getMonthValue() < 10 ? "0" + orderDate.getMonthValue() : orderDate.getMonthValue()) + "/" + orderDate.getYear();
    }

    /**
     * Getter pentru ordarDate
     * @return LocalDateTime
     */
    public LocalDateTime getDate() {
        return orderDate;
    }

    /**
     * Getter pentru clientID
     * @return int
     */
    public int getClientID() {
        return clientID;
    }

    @Override
    public int hashCode() {
        String date = getDate(orderDate);
        String format = date + "" + clientID + "" + (orderID < 100 ? (orderID < 10 ? "00" + orderID : "0" + orderID) : orderID);
        return Integer.parseInt(format);
    }

    /**
     * Getter pentru data comenzii sub forma mmdd
     * @param dt
     * @return
     */
    private String getDate(LocalDateTime dt) {
        int day = dt.getDayOfMonth();
        String day2 = day < 10 ? "0" + day : day + "";
        int month = dt.getMonthValue();
        String month2 = month < 10 ? "0" + month : month + "";
        return month2 + "" + day2;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", orderDate=" + orderDate +
                '}';
    }
}
