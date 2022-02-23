package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care desccrie continutul unui produs compus
 */
public class CompositeProduct extends MenuItem implements Serializable {
    private String title;
    private List<BaseProduct> products=new ArrayList<>();

    public CompositeProduct(String title, List<BaseProduct> products) {
        this.title = title;
        this.products = products;
    }

    public CompositeProduct() {
    }

    public CompositeProduct(String title) {
        this.title = title;
    }

    /**
     * Getter pentru produsele din componenta produsului compus
     * @return lista de produse simple
     */
    public List<BaseProduct> getProducts() {
        return products;
    }

    /**
     * Setter pentru produsele din componenta produsului compus
     * @param products lista de produse simple, produsele simple care alcatuiesc produsul compus
     */
    public void setProducts(List<BaseProduct> products) {
        this.products = products;
    }

    /**
     * Getter pentru denumira produsului
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter pentru denumirea produsului
     * @param title String, noul titlu
     */
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        String s="\'"+title+"\'";
        for(BaseProduct p:products){
            s=s+p.toString();
        }
        return s;
    }

    /**
     * Metoda care genereaza pretul total am prodului compus
     * @return int
     */
    @Override
    public int computePrice(){
        int price=0;
        for(BaseProduct p:products){
            price+=p.computePrice();
        }
        return price;
    }
}
