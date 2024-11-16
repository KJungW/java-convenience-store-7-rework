package store.constant.exception_message;

public enum ServiceExceptionMessage {
    INVALID_PROMOTIONS_IN_PRODUCT("상품에 존재하는 프로모션이 유효하지 않습니다."),
    NOT_SAME_PRODUCT("동일한 상품이 아닙니다."),
    NOT_ENOUGH_PRODUCT_QUANTITY("상품의 수량이 충분하지 않습니다.");

    private String message;

    ServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
