package pl.pwr.ite.shop.api;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    private String description;
    private int quantity;
    public Item(String description, int quantity){
        Objects.requireNonNull(description);
        assert (quantity >= 0 );
        this.description = description;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
