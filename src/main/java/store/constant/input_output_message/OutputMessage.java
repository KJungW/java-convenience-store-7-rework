package store.constant.input_output_message;

public enum OutputMessage {
    RECEIPT_START("==============W 편의점================"),
    RECEIPT_PURCHASE_HEADER("상품명         \t수량           \t금액"),
    RECEIPT_PURCHASE_CONTENT("%-15s\t%-15d\t%,d"),
    RECEIPT_GIFT_START("=============증 정==================="),
    RECEIPT_GIFT_CONTENT("%-15s\t%s"),
    RECEIPT_PRICE_START("====================================="),
    RECEIPT_PURCHASE_PRICE("총구매액      %-10d %,d"),
    RECEIPT_GIFT_PRICE("행사할인                -%,d"),
    RECEIPT_MEMBERSHIP_DISCOUNT("멤버십할인               -%,d"),
    RECEIPT_PRICE_TO_PAY("내실돈                   %,d");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}