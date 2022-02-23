package business;

import java.io.Serializable;

/**
 * Clasa care descrie continutul unui produs simplu.
 */
public class BaseProduct extends MenuItem implements Serializable {
    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public BaseProduct() {
    }

    public BaseProduct(String title, Double rating, Integer calories,Integer protein ,Integer fat, Integer sodium, Integer price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.fat = fat;
        this.protein=protein;
        this.sodium = sodium;
        this.price = price;
    }

    /**
     * Getter pentru denumirea produsului.
     * @return string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter pentru modificarea denumirii unui produs
     * @param title noua denumire a produsului
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter pentru rating-ul produsului
     * @return double
     */
    public double getRating() {
        return rating;
    }

    /**
     * Setter pentru rating-ul produsului.
     * @param rating noua valoare de rating a produsului
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Getter pentru caloriile produsului.
     * @return int
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Stter pentru caloriile unui produs
     * @param calories noua valoare a caloriilor produsului
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Getter pentru valoarea de grasimi din produs
     * @return int
     */
    public int getFat() {
        return fat;
    }

    /**
     * Setter pentru valoare gradimilor din produs
     * @param fat noua valoarea a atributului fat
     */
    public void setFat(int fat) {
        this.fat = fat;
    }

    /**
     * Getter pentru valoarea de sare din produs
     * @return int
     */
    public int getSodium() {
        return sodium;
    }

    /**
     * Setter pentru valoarea de sare din produs
     * @param sodium noua valoarea a atributuilui sodium
     */
    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    /**
     * Setter pentru stabilirea pretului produsului
     * @param price noul pret al produsului
     */
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  "{'" + title + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", price=" + price+"}; ";
    }

    /**
     * Getter pentru pretul produsului
     * @return int
     */
    @Override
    public int computePrice() {
        return price;
    }
}
