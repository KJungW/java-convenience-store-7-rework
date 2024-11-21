package store.domain;

import store.constant.exception_message.DomainExceptionMessage;

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

    public Product(String name, int price, int promotionQuantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.commonQuantity = DEFAULT_QUANTITY;
        this.promotionQuantity = promotionQuantity;
        this.promotionName = promotionName;
    }

    public Product(String name, int price, int commonQuantity, int promotionQuantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.commonQuantity = commonQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotionName = promotionName;
    }

    public Product copy() {
        return new Product(name, price, commonQuantity, promotionQuantity, promotionName);
    }

    public boolean checkIsPromoted() {
        return !promotionName.isEmpty();
    }

    public void addCommonQuantity(int quantity) {
        commonQuantity += quantity;
    }

    public void subtractCommonQuantity(int quantity) {
        if (commonQuantity - quantity < 0) {
            throw new IllegalArgumentException(DomainExceptionMessage.QUANTITY_SUBTRACTION_IS_IMPOSSIBLE.getMessage());
        }
        commonQuantity -= quantity;
    }

    public void addPromotionQuantity(int quantity) {
        promotionQuantity += quantity;
    }

    public void subtractPromotionQuantity(int quantity) {
        if (promotionQuantity - quantity < 0) {
            throw new IllegalArgumentException(DomainExceptionMessage.QUANTITY_SUBTRACTION_IS_IMPOSSIBLE.getMessage());
        }
        promotionQuantity -= quantity;
    }

    public boolean isEmptyQuantity() {
        return commonQuantity + promotionQuantity <= DEFAULT_QUANTITY;
    }

    public boolean isEnoughQuantity(int quantity) {
        return commonQuantity + promotionQuantity >= quantity;
    }

    public boolean isEnoughCommonQuantity(int quantity) {
        return commonQuantity >= quantity;
    }

    public boolean isEnoughPromotionQuantity(int quantity) {
        return promotionQuantity >= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCommonQuantity() {
        return commonQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public String getPromotionName() {
        return promotionName;
    }
}
