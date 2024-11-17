package store.dto;

public class GiftedItem {

    private final String productName;
    private final int price;
    private final int quantity;

    public GiftedItem(String productName, int price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
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
