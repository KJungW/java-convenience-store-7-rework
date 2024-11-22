package store.constant.view_message;

public enum InputExceptionMessage {
    INPUT_IS_WRONG_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_DOSE_NOT_EXIST("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PRODUCT_QUANTITY_DOES_NOT_ENOUGH("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INPUT_IS_NOT_YES_OR_NO("형식에 맞지 않는 입력입니다. 'Y'나 'N'으로 다시 입력해 주세요."),
    INPUT_IS_INVALID("잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    InputExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
