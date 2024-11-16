package store.constant.input_output_message;

public enum InputGuideMessage {
    BASKET_ITEMS_INPUT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

    private String message;

    InputGuideMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
