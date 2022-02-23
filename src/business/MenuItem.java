package business;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
  /**
   * Metoda care trebuie implementata de clasele care extind MenuItem.
   * Metoda calculeaza pretul total pentru fiecare produs
   * @return int
   */
  abstract public int computePrice();
}
