package store.generator;

import java.time.LocalDateTime;
import store.domain.Promotion;

public class PromotionGenerator {

    public static Promotion makeValid(String name, int purchaseQuantity, int giftQuantity) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(3);
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);
        return new Promotion(name, purchaseQuantity, giftQuantity, startDate, endDate);
    }

    public static Promotion makeInvalid(String name, int purchaseQuantity, int giftQuantity) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(6);
        LocalDateTime endDate = LocalDateTime.now().minusDays(3);
        return new Promotion(name, purchaseQuantity, giftQuantity, startDate, endDate);
    }

}
