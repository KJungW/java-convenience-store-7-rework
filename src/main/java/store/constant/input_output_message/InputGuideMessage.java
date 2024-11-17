package store.constant.input_output_message;

public enum InputGuideMessage {
    BASKET_ITEMS_INPUT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    ADDITIONAL_GIFT_ITEM_ACCEPTANCE_INPUT("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");

    private String message;

    InputGuideMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
