package store.service;

import java.util.List;
import store.constant.exception_message.ServiceExceptionMessage;
import store.domain.BasketItem;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class BasketService {

    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public BasketService(BasketItemRepository basketItemRepository, ProductRepository productRepository,
            PromotionRepository promotionRepository) {
        this.basketItemRepository = basketItemRepository;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
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

    public void subtractBasketItemQuantity(String productName, int quantity) {
        validateBasketItemExistence(productName);
        BasketItem basketItem = basketItemRepository.find(productName);
        validateQuantitySubtractionIsPossible(basketItem, quantity);
        basketItem.subtractQuantity(quantity);
        if (basketItem.getQuantity() > 0) {
            basketItemRepository.update(basketItem);
            return;
        }
        basketItemRepository.delete(basketItem.getName());
    }

    public boolean checkEnoughQuantityForBasketItem(BasketItem basketItem) {
        Product product = productRepository.find(basketItem.getName());
        boolean isPromoted = checkPromotionInProductIsAvailable(product);
        if (isPromoted) {
            return product.isEnoughQuantity(basketItem.getQuantity());
        }
        return product.isEnoughCommonQuantity(basketItem.getQuantity());
    }

    public boolean checkProductExistenceForBasketItem(BasketItem basketItem) {
        return productRepository.checkExistence(basketItem.getName());
    }

    private void validateEnoughQuantityForBasketItem(BasketItem basketItem) {
        if (!checkEnoughQuantityForBasketItem(basketItem)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.PRODUCT_QUANTITY_IS_NOT_ENOUGH.getMessage());
        }
    }

    private void validateProductExistenceForBasketItem(BasketItem basketItem) {
        if (!checkProductExistenceForBasketItem(basketItem)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.PRODUCT_DOES_NOT_EXIST.getMessage());
        }
    }

    private void validateBasketItemExistence(String productName) {
        if (!checkBasketItemExistence(productName)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.BASKET_ITEM_DOES_NOT_EXIST.getMessage());
        }
    }

    private boolean checkBasketItemExistence(String productName) {
        return basketItemRepository.checkExistence(productName);
    }

    private void validateQuantitySubtractionIsPossible(BasketItem basketItem, int quantity) {
        if (!checkQuantitySubtractionIsPossible(basketItem, quantity)) {
            throw new IllegalArgumentException(ServiceExceptionMessage.NEGATIVE_QUANTITY_IS_NOT_ALLOWED.getMessage());
        }
    }

    private boolean checkQuantitySubtractionIsPossible(BasketItem basketItem, int quantity) {
        return basketItem.checkQuantitySubtractionIsPossible(quantity);
    }

    private boolean checkPromotionInProductIsAvailable(Product product) {
        if (!product.checkIsPromoted()) {
            return false;
        }
        Promotion promotion = promotionRepository.find(product.getPromotionName());
        return promotion.isAvailable();
    }
}
