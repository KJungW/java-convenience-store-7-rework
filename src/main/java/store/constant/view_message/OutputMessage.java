package store.constant.view_message;

public enum OutputMessage {
    WELCOME_GREETING("안녕하세요. W편의점입니다."),
    PRODUCT_INTRODUCTION_START("현재 보유하고 있는 상품입니다."),
    COMMON_PRODUCT_DETAIL("- %s %,d원 %s"),
    PROMOTION_PRODUCT_DETAIL("- %s %,d원 %s %s"),
    OUT_OF_QUANTITY("재고 없음"),
    QUANTITY_POSTFIX("%d개"),
    RECEIPT_START("============W 편의점============"),
    RECEIPT_PURCHASE_HEADER("상품명\t\t\t\t수량\t\t\t\t\t금액"),
    RECEIPT_PURCHASE_CONTENT("%-10s\t%-10d\t%,d"),
    RECEIPT_GIFT_START("=============증 정============="),
    RECEIPT_GIFT_CONTENT("%-10s\t%s"),
    RECEIPT_PRICE_START("==============================="),
    RECEIPT_PURCHASE_PRICE("총구매액\t\t\t%-10d\t%,d"),
    RECEIPT_GIFT_PRICE("행사할인\t\t\t\t\t\t\t\t\t-%,d"),
    RECEIPT_MEMBERSHIP_DISCOUNT("멤버십할인\t\t\t\t\t\t\t\t-%,d"),
    RECEIPT_PRICE_TO_PAY("내실돈\t\t\t\t\t\t\t\t\t\t%,d"),
    ERROR_MESSAGE_PREFIX("[ERROR] ");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
