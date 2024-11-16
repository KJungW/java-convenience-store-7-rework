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

    public void addBasketItem(List<BasketItem> newBasketItems) {
        for (BasketItem item : newBasketItems) {
            if (!checkBasketItemQuantityIsEnough(item)) {
                throw new IllegalArgumentException(ServiceExceptionMessage.NOT_ENOUGH_PRODUCT_QUANTITY.getMessage());
            }
            basketItemRepository.add(item);
        }
    }

    private boolean checkBasketItemQuantityIsEnough(BasketItem basketItem) {
        Product product = productRepository.find(basketItem.getName());
        return product.isEnoughQuantity(basketItem.getQuantity());
    }
}
