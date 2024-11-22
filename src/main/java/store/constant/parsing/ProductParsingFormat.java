package store.constant.parsing;

public enum ProductParsingFormat {
    NAME(0),
    PRICE(1),
    QUANTITY(2),
    PROMOTION_NAME(3);

    private int index;

    ProductParsingFormat(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
