package store.constant.parsing;

public enum BasketItemsParsingFormat {
    NAME(0),
    QUANTITY(1);

    private int index;

    BasketItemsParsingFormat(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
