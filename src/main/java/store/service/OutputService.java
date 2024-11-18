package store.service;

import java.util.List;
import store.constant.input_output_message.OutputMessage;
import store.domain.Product;
import store.dto.GiftedItem;
import store.dto.PurchasedItem;
import store.dto.Receipt;
import store.repository.ProductRepository;
import store.view.OutputView;

public class OutputService {

    private final OutputView outputView;
    private final ProductRepository productRepository;

    public OutputService(OutputView outputView, ProductRepository productRepository) {
        this.outputView = outputView;
        this.productRepository = productRepository;
    }

    public void printWelcomeGreeting() {
        outputView.printContent("안녕하세요. W편의점입니다.");
    }

    public void printProductGuideMessage() {
        outputView.printContent("현재 보유하고 있는 상품입니다.");
        outputView.printBlankLine();
    }

    public void printProductsInStore() {
        List<Product> allProduct = productRepository.findAll();
        for (Product product : allProduct) {
            if (product.checkIsPromoted()) {
                printPromotionProduct(product);
                printCommonProduct(product);
                continue;
            }
            printCommonProduct(product);
        }
        outputView.printBlankLine();
    }

    private void printCommonProduct(Product product) {
        String quantityContent = makeQuantityContent(product.getCommonQuantity());
        String content = String.format("- %s %,d원 %s",
                product.getName(), product.getPrice(), quantityContent);
        outputView.printContent(content);
    }

    private void printPromotionProduct(Product product) {
        String quantityContent = makeQuantityContent(product.getPromotionQuantity());
        String content = String.format("- %s %,d원 %s %s",
                product.getName(), product.getPrice(), quantityContent, product.getPromotionName());
        outputView.printContent(content);
    }

    private String makeQuantityContent(int quantity) {
        if (quantity <= 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    public void printReceipt(Receipt receipt) {
        outputView.printContent(OutputMessage.RECEIPT_START.getMessage());
        printPurchaseInformationInReceipt(receipt.getPurchasedItems());
        printGiftInformationInReceipt(receipt.getGiftedItems());
        printPriceInformationInReceipt(receipt);
    }

    private void printPurchaseInformationInReceipt(List<PurchasedItem> items) {
        outputView.printContent(OutputMessage.RECEIPT_PURCHASE_HEADER.getMessage());
        for (PurchasedItem item : items) {
            String content = String.format(OutputMessage.RECEIPT_PURCHASE_CONTENT.getMessage(),
                    item.getProductName(), item.getQuantity(), item.getPrice());
            outputView.printContent(content);
        }
    }

    private void printGiftInformationInReceipt(List<GiftedItem> items) {
        outputView.printContent(OutputMessage.RECEIPT_GIFT_START.getMessage());
        for (GiftedItem item : items) {
            String content = String.format(OutputMessage.RECEIPT_GIFT_CONTENT.getMessage(),
                    item.getProductName(), item.getQuantity(), item.getPrice());
            outputView.printContent(content);
        }
    }

    private void printPriceInformationInReceipt(Receipt receipt) {
        outputView.printContent(OutputMessage.RECEIPT_PRICE_START.getMessage());
        printPurchasePrice(receipt);
        printGiftPrice(receipt);
        printMembershipDiscount(receipt);
        printPriceToPay(receipt);
    }

    private void printPurchasePrice(Receipt receipt) {
        String purchasePriceContent = String.format(OutputMessage.RECEIPT_PURCHASE_PRICE.getMessage(),
                receipt.calculatePurchasePrice());
        outputView.printContent(purchasePriceContent);
    }

    private void printGiftPrice(Receipt receipt) {
        String giftPriceContent = String.format(OutputMessage.RECEIPT_GIFT_PRICE.getMessage(),
                receipt.calculateGiftPrice());
        outputView.printContent(giftPriceContent);
    }

    private void printMembershipDiscount(Receipt receipt) {
        String membershipDiscountContent = String.format(OutputMessage.RECEIPT_MEMBERSHIP_DISCOUNT.getMessage(),
                receipt.getMembershipDiscount());
        outputView.printContent(membershipDiscountContent);
    }

    private void printPriceToPay(Receipt receipt) {
        String priceToPayContent = String.format(OutputMessage.RECEIPT_PRICE_TO_PAY.getMessage(),
                receipt.calculatePriceToPay());
        outputView.printContent(priceToPayContent);
    }
}
