package store.constant.parsing;

public enum DateParsingFormat {
    YEAR(0),
    MONTH(1),
    DAY(2);

    private int index;

    DateParsingFormat(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
