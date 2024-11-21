package store.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constant.exception_message.RepositoryExceptionMessage;
import store.domain.BasketItem;

public class BasketItemRepository {

    private Map<String, BasketItem> basketItems = new HashMap<>();

    public void add(BasketItem basketItem) {
        validateDuplicate(basketItem.getName());
        basketItems.put(basketItem.getName(), basketItem);
    }

    public BasketItem find(String basketItemName) {
        validateExistence(basketItemName);
        return basketItems.get(basketItemName).copy();
    }

    public List<BasketItem> findAll() {
        return basketItems.keySet().stream()
                .map(key -> basketItems.get(key))
                .map(BasketItem::copy)
                .toList();
    }

    public void update(BasketItem basketItem) {
        validateExistence(basketItem.getName());
        basketItems.put(basketItem.getName(), basketItem);
    }

    public void delete(String basketItemName) {
        validateExistence(basketItemName);
        basketItems.remove(basketItemName);
    }

    public void clear() {
        basketItems.clear();
    }

    public boolean checkExistence(String basketItemName) {
        return basketItems.containsKey(basketItemName);
    }

    private void validateDuplicate(String basketItemName) {
        if (checkExistence(basketItemName)) {
            throw new IllegalArgumentException(RepositoryExceptionMessage.DATA_ALREADY_EXIST.getMessage());
        }
    }

    private void validateExistence(String basketItemName) {
        if (!checkExistence(basketItemName)) {
            throw new IllegalArgumentException(RepositoryExceptionMessage.DATA_DOES_NOT_EXIST.getMessage());
        }
    }
}
