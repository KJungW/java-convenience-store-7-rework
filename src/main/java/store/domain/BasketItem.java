package store.domain;

public class BasketItem {

    private String name;
    private int quantity;

    public BasketItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BasketItem copy() {
        return new BasketItem(name, quantity);
    }
}
