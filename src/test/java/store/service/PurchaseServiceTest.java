package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.BasketItem;
import store.domain.Product;
import store.dto.GiftedItem;
import store.dto.PurchasedItem;
import store.dto.Receipt;
import store.generator.PromotionGenerator;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class PurchaseServiceTest {

    private PurchaseService purchaseService;
    private BasketItemRepository basketItemRepository;
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;

    @BeforeEach
    void beforeEach() {
        basketItemRepository = new BasketItemRepository();
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        purchaseService = new PurchaseService(basketItemRepository, productRepository, promotionRepository);
    }

    @DisplayName("장바구니 아이템을 구매할 수 있다")
    @Test
    void 장바구니_아이템을_구매할_수_있다() {
        setPurchaseEnvironment();

        Receipt receipt = purchaseService.purchase();

        checkReceipt(receipt);
        checkProductQuantitySubtraction("웰치스", 10, 5);
        checkProductQuantitySubtraction("콜라", 5, 0);
        checkProductQuantitySubtraction("사이다", 5, 0);
        checkBasketReset();
    }

    void setPurchaseEnvironment() {
        promotionRepository.add(PromotionGenerator.makeValid("탄산2+1", 2, 1));
        productRepository.add(new Product("웰치스", 3000, 10, 10, "탄산2+1"));
        productRepository.add(new Product("콜라", 3000, 10, 10, "탄산2+1"));
        productRepository.add(new Product("사이다", 1000, 10));
        basketItemRepository.add(new BasketItem("웰치스", 5));
        basketItemRepository.add(new BasketItem("콜라", 15));
        basketItemRepository.add(new BasketItem("사이다", 5));
    }

    void checkReceipt(Receipt receipt) {
        checkPurchasedItemsInReceipt(receipt);
        checkGiftedItemsInReceipt(receipt);
        checkMembershipDiscount(receipt);
    }

    void checkPurchasedItemsInReceipt(Receipt receipt) {
        List<PurchasedItem> purchasedItems = List.of(
                new PurchasedItem("웰치스", 3000, 5),
                new PurchasedItem("콜라", 3000, 15),
                new PurchasedItem("사이다", 1000, 5));
        assertThat(receipt.getPurchasedItems())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(purchasedItems);
    }

    void checkGiftedItemsInReceipt(Receipt receipt) {
        List<GiftedItem> giftedItem = List.of(
                new GiftedItem("웰치스", 3000, 1),
                new GiftedItem("콜라", 3000, 3));
        assertThat(receipt.getGiftedItems())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(giftedItem);
    }

    void checkMembershipDiscount(Receipt receipt) {
        assertThat(receipt.getMembershipDiscount()).isEqualTo(0);
    }

    void checkProductQuantitySubtraction(String productName, int commonQuantity, int promotionQuantity) {
        Product product = productRepository.find(productName);
        assertThat(product.getPromotionQuantity()).isEqualTo(promotionQuantity);
        assertThat(product.getCommonQuantity()).isEqualTo(commonQuantity);
    }

    void checkBasketReset() {
        List<BasketItem> allItems = basketItemRepository.findAll();
        assertThat(allItems).isEmpty();
    }
}