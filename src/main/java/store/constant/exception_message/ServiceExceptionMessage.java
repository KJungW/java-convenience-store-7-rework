package store.constant.exception_message;

public enum ServiceExceptionMessage {
    INVALID_PROMOTIONS_IN_PRODUCT("상품에 존재하는 프로모션이 유효하지 않습니다."),
    NOT_SAME_PRODUCT("동일한 상품이 아닙니다."),
    NOT_ENOUGH_PRODUCT_QUANTITY("상품의 수량이 충분하지 않습니다."),
    NOT_EXIST_PRODUCT("상품이 존재하지 않습니다."),
    NOT_EXIST_BASKET_ITEM("장바구니 아이템이 존재하지 않습니다."),
    IMPOSSIBLE_QUANTITY_SUBTRACTION("기존의 수량을 초과하는 수량을 차감할 수 없습니다.");

    private String message;

    ServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
