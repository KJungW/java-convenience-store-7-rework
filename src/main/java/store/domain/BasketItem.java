package store.domain;

import store.constant.DefaultValue;
import store.constant.exception_message.DomainExceptionMessage;

public class BasketItem {

    private String name;
    private int quantity;

    public BasketItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        if (!checkQuantitySubtractionIsPossible(quantity)) {
            throw new IllegalArgumentException(DomainExceptionMessage.QUANTITY_SUBTRACTION_IS_IMPOSSIBLE.getMessage());
        }
        this.quantity -= quantity;
    }

    public boolean checkQuantitySubtractionIsPossible(int quantity) {
        return (this.quantity - quantity) >= DefaultValue.MINIMUM_QUANTITY;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BasketItem copy() {
        return new BasketItem(name, quantity);
    }
}
