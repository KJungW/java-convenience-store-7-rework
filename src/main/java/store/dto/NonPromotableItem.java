package store.dto;

public class NonPromotableItem {

    private final String productName;
    private final int quantity;

    public NonPromotableItem(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
