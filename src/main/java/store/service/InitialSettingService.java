package store.service;

import java.time.LocalDateTime;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.utility.FileUtility;

public class InitialSettingService {

    private static final int DEFAULT_TIME_VALUE = 0;

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public InitialSettingService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initialize(String productsFileName, String promotionFileName) {
        setInitialPromotionByFile(promotionFileName);
        setInitialProductByFile(productsFileName);
    }

    private void setInitialPromotionByFile(String fileName) {
        List<String> rawPromotions = FileUtility.readFileBySpace(fileName);
        rawPromotions.removeFirst();
        List<Promotion> promotions = rawPromotions.stream()
                .map(this::parsePromotion)
                .toList();
        promotions.forEach(promotionRepository::add);
    }

    private Promotion parsePromotion(String rawPromotion) {
        List<String> promotionParts = List.of(rawPromotion.split(","));
        String name = promotionParts.get(0);
        int purchaseCount = Integer.parseInt(promotionParts.get(1));
        int giftCount = Integer.parseInt(promotionParts.get(2));
        LocalDateTime startDate = parseDate(promotionParts.get(3));
        LocalDateTime endDate = parseDate(promotionParts.get(4));
        return new Promotion(name, purchaseCount, giftCount, startDate, endDate);
    }

    private LocalDateTime parseDate(String rawDate) {
        List<String> dateParts = List.of(rawDate.split("-"));
        int year = Integer.parseInt(dateParts.get(0));
        int month = Integer.parseInt(dateParts.get(1));
        int day = Integer.parseInt(dateParts.get(2));
        return LocalDateTime.of(year, month, day, DEFAULT_TIME_VALUE, DEFAULT_TIME_VALUE, DEFAULT_TIME_VALUE);
    }

    private void setInitialProductByFile(String fileName) {
        List<String> rawProducts = FileUtility.readFileBySpace(fileName);
        rawProducts.removeFirst();
        List<Product> products = rawProducts.stream()
                .map(this::parseProduct)
                .toList();
        products.forEach(productRepository::add);
    }

    private Product parseProduct(String rawProduct) {
        List<String> promotionParts = List.of(rawProduct.split(","));
        String name = promotionParts.get(0);
        int price = Integer.parseInt(promotionParts.get(1));
        int quantity = Integer.parseInt(promotionParts.get(2));
        String promotionName = promotionParts.get(3);
        return new Product(name, price, quantity, 0, promotionName);
    }
}
