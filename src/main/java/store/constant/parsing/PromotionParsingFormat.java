package store.constant.parsing;

public enum PromotionParsingFormat {
    NAME(0),
    PURCHASE_COUNT(1),
    GIFT_COUNT(2),
    START_DATE(3),
    END_DATE(4);

    private int index;

    PromotionParsingFormat(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
