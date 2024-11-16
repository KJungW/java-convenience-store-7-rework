package store.service;

import java.time.LocalDateTime;
import java.util.List;
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

    private void setInitialPromotionByFile(String fileName) {
        List<String> rawPromotions = FileUtility.readFileBySpace(fileName);
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
        List<String> dateParts = List.of(rawDate.split(","));
        int year = Integer.parseInt(dateParts.get(0));
        int month = Integer.parseInt(dateParts.get(1));
        int day = Integer.parseInt(dateParts.get(2));
        return LocalDateTime.of(year, month, day, DEFAULT_TIME_VALUE, DEFAULT_TIME_VALUE, DEFAULT_TIME_VALUE);
    }
}
