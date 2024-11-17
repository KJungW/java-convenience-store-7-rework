package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.BasketItem;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.AdditionalGiftItem;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class PromotionService {

    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public PromotionService(
            BasketItemRepository basketItemRepository,
            ProductRepository productRepository,
            PromotionRepository promotionRepository) {
        this.basketItemRepository = basketItemRepository;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public List<AdditionalGiftItem> findAdditionalGiftItem() {
        List<BasketItem> allBasketItems = basketItemRepository.findAll();
        List<AdditionalGiftItem> additionalGiftItems = new ArrayList<>();
        for (BasketItem item : allBasketItems) {
            int additionalGiftCount = calculateAdditionalGiftCount(item);
            if (additionalGiftCount != 0) {
                additionalGiftItems.add(new AdditionalGiftItem(item.getName(), additionalGiftCount));
            }
        }
        return additionalGiftItems;
    }

    private int calculateAdditionalGiftCount(BasketItem basketItem) {
        Product product = productRepository.find(basketItem.getName());
        if (!checkPromotionInProductIsAvailable(product)) {
            return 0;
        }
        Promotion promotion = promotionRepository.find(product.getPromotionName());
        int additionalGiftCount = promotion.calculateAdditionalGiftCount(basketItem.getQuantity());
        if (!product.isEnoughPromotionQuantity(basketItem.getQuantity() + additionalGiftCount)) {
            return 0;
        }
        return additionalGiftCount;
    }

    private boolean checkPromotionInProductIsAvailable(Product product) {
        Promotion promotion = promotionRepository.find(product.getPromotionName());
        return product.checkIsPromoted() && promotion.isAvailable();
    }
}
