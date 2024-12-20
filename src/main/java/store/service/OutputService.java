package store.service;

import java.util.List;
import store.constant.DefaultValue;
import store.constant.view_message.OutputMessage;
import store.domain.Product;
import store.dto.GiftedItem;
import store.dto.PurchasedItem;
import store.dto.Receipt;
import store.repository.ProductRepository;
import store.view.OutputView;

public class OutputService {

    private final OutputView outputView;
    private final ProductRepository productRepository;
    private final PromotionService promotionService;

    public OutputService(OutputView outputView, ProductRepository productRepository,
            PromotionService promotionService) {
        this.outputView = outputView;
        this.productRepository = productRepository;
        this.promotionService = promotionService;
    }

    public void printWelcomeGreeting() {
        outputView.printContent(OutputMessage.WELCOME_GREETING.getMessage());
    }

    public void printProductGuideMessage() {
        outputView.printContent(OutputMessage.PRODUCT_INTRODUCTION_START.getMessage());
        outputView.printBlankLine();
    }

    public void printProductsInStore() {
        List<Product> allProduct = productRepository.findAll();
        for (Product product : allProduct) {
            if (promotionService.checkPromotionInProductIsAvailable(product)) {
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
        String content = String.format(OutputMessage.COMMON_PRODUCT_DETAIL.getMessage(),
                product.getName(), product.getPrice(), quantityContent);
        outputView.printContent(content);
    }

    private void printPromotionProduct(Product product) {
        String quantityContent = makeQuantityContent(product.getPromotionQuantity());
        String content = String.format(OutputMessage.PROMOTION_PRODUCT_DETAIL.getMessage(),
                product.getName(), product.getPrice(), quantityContent, product.getPromotionName());
        outputView.printContent(content);
    }

    private String makeQuantityContent(int quantity) {
        if (quantity <= DefaultValue.MINIMUM_QUANTITY) {
            return OutputMessage.OUT_OF_QUANTITY.getMessage();
        }
        return String.format(OutputMessage.QUANTITY_POSTFIX.getMessage(), quantity);
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
                    item.getProductName(), item.getQuantity(), item.calculateFullPrice());
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
                receipt.calculatePurchasePrice(), receipt.calculatePurchasedProductQuantity());
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
