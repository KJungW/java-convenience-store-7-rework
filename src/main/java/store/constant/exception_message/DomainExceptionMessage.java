package store.constant.exception_message;

public enum DomainExceptionMessage {
    QUANTITY_SUBTRACTION_IS_IMPOSSIBLE("재고 부족으로 수량 차감이 불가능합니다");

    private String message;

    DomainExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
