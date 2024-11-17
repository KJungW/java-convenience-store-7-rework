package store.service;

import java.util.List;
import store.constant.exception_message.ServiceExceptionMessage;
import store.domain.BasketItem;
import store.domain.Product;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;

public class BasketService {

    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;

    public BasketService(BasketItemRepository basketItemRepository, ProductRepository productRepository) {
        this.basketItemRepository = basketItemRepository;
        this.productRepository = productRepository;
    }

    public void addBasketItems(List<BasketItem> newBasketItems) {
        for (BasketItem item : newBasketItems) {
            validateProductExistenceForBasketItem(item);
            validateEnoughQuantityForBasketItem(item);
            basketItemRepository.add(item);
        }
    }

    public void addBasketItemQuantity(String productName, int quantity) {
        validateBasketItemExistence(productName);
        BasketItem basketItem = basketItemRepository.find(productName);
        basketItem.addQuantity(quantity);
        validateEnoughQuantityForBasketItem(basketItem);
        basketItemRepository.update(basketItem);
    }

    private void validateEnoughQuantityForBasketItem(BasketItem basketItem) {
        if (!checkEnoughQuantityForBasketItem(basketItem)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.NOT_ENOUGH_PRODUCT_QUANTITY.getMessage());
        }
    }

    private boolean checkEnoughQuantityForBasketItem(BasketItem basketItem) {
        Product product = productRepository.find(basketItem.getName());
        return product.isEnoughQuantity(basketItem.getQuantity());
    }

    private void validateProductExistenceForBasketItem(BasketItem basketItem) {
        if (!checkProductExistenceForBasketItem(basketItem)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.NOT_EXIST_PRODUCT.getMessage());
        }
    }

    private boolean checkProductExistenceForBasketItem(BasketItem basketItem) {
        return productRepository.checkExistence(basketItem.getName());
    }

    private void validateBasketItemExistence(String productName) {
        if (!checkBasketItemExistence(productName)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.NOT_EXIST_BASKET_ITEM.getMessage());
        }
    }

    private boolean checkBasketItemExistence(String productName) {
        return basketItemRepository.checkExistence(productName);
    }
}
