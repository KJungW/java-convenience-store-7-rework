package store.domain;

public class Product {

    private static final int DEFAULT_QUANTITY = 0;
    private static final String DEFAULT_PROMOTION_NAME = "";

    private final String name;
    private final int price;
    private int commonQuantity;
    private int promotionQuantity;
    private final String promotionName;

    public Product(String name, int price, int commonQuantity) {
        this.name = name;
        this.price = price;
        this.commonQuantity = commonQuantity;
        this.promotionQuantity = DEFAULT_QUANTITY;
        this.promotionName = DEFAULT_PROMOTION_NAME;
    }


    public Product(String name, int price, int commonQuantity, int promotionQuantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.commonQuantity = commonQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotionName = promotionName;
    }

    public String getName() {
        return name;
    }

    public Product copy() {
        return new Product(name, price, commonQuantity, promotionQuantity, promotionName);
    }
}
