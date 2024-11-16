package store.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.exception_message.RepositoryExceptionMessage;
import store.domain.Promotion;

public class PromotionRepository {

    private Map<String, Promotion> promotions = new HashMap<>();

    public void add(Promotion promotion) {
        validateDuplicate(promotion.getName());
        promotions.put(promotion.getName(), promotion);
    }

    public Promotion find(String promotionName) {
        validateExistence(promotionName);
        return promotions.get(promotionName).copy();
    }

    public List<Promotion> findAll() {
        return promotions.keySet().stream()
                .map(key -> promotions.get(key))
                .map(Promotion::copy)
                .toList();
    }

    public void update(Promotion promotion) {
        validateExistence(promotion.getName());
        promotions.put(promotion.getName(), promotion);
    }

    public boolean checkExistence(String promotionName) {
        return promotions.containsKey(promotionName);
    }

    private void validateDuplicate(String promotionName) {
        if (checkExistence(promotionName)) {
            throw new IllegalArgumentException(RepositoryExceptionMessage.DATA_ALREADY_EXIST.getMessage());
        }
    }

    private void validateExistence(String promotionName) {
        if (!checkExistence(promotionName)) {
            throw new IllegalArgumentException(RepositoryExceptionMessage.DATA_NOT_EXIST.getMessage());
        }
    }
}
