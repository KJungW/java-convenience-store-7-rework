package store.constant.exception_message;

public enum ServiceExceptionMessage {
    PROMOTION_IN_PRODUCT_IS_INVALID("상품에 존재하는 프로모션이 유효하지 않습니다."),
    PRODUCT_DOES_NOT_SAME("동일한 상품이 아닙니다."),
    PRODUCT_QUANTITY_IS_NOT_ENOUGH("상품의 수량이 충분하지 않습니다."),
    PRODUCT_DOES_NOT_EXIST("상품이 존재하지 않습니다."),
    BASKET_ITEM_DOES_NOT_EXIST("장바구니 아이템이 존재하지 않습니다."),
    NEGATIVE_QUANTITY_IS_NOT_ALLOWED("기존의 수량을 초과하는 수량을 차감할 수 없습니다.");

    private String message;

    ServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
