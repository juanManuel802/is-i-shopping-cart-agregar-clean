package isi.shoppingCart.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Purchase {
    private List<CartItem> items;
    private double total;
    private LocalDateTime date;

    public Purchase(List<CartItem> items, double total) {
        this.items = new ArrayList<CartItem>(items);
        this.total = total;
        this.date = LocalDateTime.now();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
