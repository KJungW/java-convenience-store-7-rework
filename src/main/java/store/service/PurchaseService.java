package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.BasketItem;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.GiftedItem;
import store.dto.PurchasedItem;
import store.dto.Receipt;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class PurchaseService {

    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public PurchaseService(
            BasketItemRepository basketItemRepository,
            ProductRepository productRepository,
            PromotionRepository promotionRepository) {
        this.basketItemRepository = basketItemRepository;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public Receipt purchase() {
        List<BasketItem> allBasketItem = basketItemRepository.findAll();
        List<PurchasedItem> purchasedItems = makePurchasedItems(allBasketItem);
        List<GiftedItem> giftedItems = makeGiftedItems(allBasketItem);
        subtractProductQuantity(allBasketItem);
        basketItemRepository.clear();
        return new Receipt(purchasedItems, giftedItems);
    }

    private List<PurchasedItem> makePurchasedItems(List<BasketItem> allBasketItem) {
        List<PurchasedItem> purchasedItems = new ArrayList<>();
        for (BasketItem item : allBasketItem) {
            Product product = productRepository.find(item.getName());
            PurchasedItem purchasedItem = new PurchasedItem(product.getName(), product.getPrice(), item.getQuantity());
            purchasedItems.add(purchasedItem);
        }
        return purchasedItems;
    }

    private List<GiftedItem> makeGiftedItems(List<BasketItem> allBasketItem) {
        List<GiftedItem> giftedItems = new ArrayList<>();
        for (BasketItem item : allBasketItem) {
            Product product = productRepository.find(item.getName());
            if (!checkPromotionInProductIsAvailable(product)) {
                continue;
            }
            GiftedItem giftedItem = makeGiftedItem(item.getQuantity(), product);
            giftedItems.add(giftedItem);
        }
        return giftedItems;
    }

    private GiftedItem makeGiftedItem(int purchaseQuantity, Product product) {
        Promotion promotion = promotionRepository.find(product.getPromotionName());
        int giftedItemQuantity = promotion.calculateGiftedItemCount(purchaseQuantity, product.getPromotionQuantity());
        return new GiftedItem(product.getName(), product.getPrice(), giftedItemQuantity);
    }

    private void subtractProductQuantity(List<BasketItem> allBasketItem) {
        for (BasketItem item : allBasketItem) {
            Product product = productRepository.find(item.getName());
            if (!checkPromotionInProductIsAvailable(product)) {
                subtractCommonProductQuantity(product, item.getQuantity());
                continue;
            }
            subtractPromotionProductQuantity(product, item.getQuantity());
        }
    }

    private void subtractCommonProductQuantity(Product product, int quantity) {
        product.subtractCommonQuantity(quantity);
        productRepository.update(product);
    }

    private void subtractPromotionProductQuantity(Product product, int quantity) {
        if (product.getPromotionQuantity() >= quantity) {
            product.subtractPromotionQuantity(quantity);
            productRepository.update(product);
            return;
        }
        int excessQuantity = quantity - product.getPromotionQuantity();
        product.subtractPromotionQuantity(product.getPromotionQuantity());
        product.subtractCommonQuantity(excessQuantity);
        productRepository.update(product);
    }

    private boolean checkPromotionInProductIsAvailable(Product product) {
        if (!product.checkIsPromoted()) {
            return false;
        }
        Promotion promotion = promotionRepository.find(product.getPromotionName());
        return promotion.isAvailable();
    }
}
