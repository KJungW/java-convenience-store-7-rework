package store.dto;

public class PurchasedItem {

    private final String productName;
    private final int price;
    private final int quantity;

    public PurchasedItem(String productName, int price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int calculateFullPrice() {
        return price * quantity;
    }

    public PurchasedItem copy() {
        return new PurchasedItem(productName, price, quantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
