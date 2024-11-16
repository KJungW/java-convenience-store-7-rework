package store.service;

import java.util.List;
import store.domain.Product;
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
}
