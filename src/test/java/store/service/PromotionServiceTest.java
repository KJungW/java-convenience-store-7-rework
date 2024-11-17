package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.BasketItem;
import store.domain.Product;
import store.dto.AdditionalGiftItem;
import store.dto.NonPromotableItem;
import store.generator.PromotionGenerator;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class PromotionServiceTest {

    private PromotionService promotionService;
    private BasketItemRepository basketItemRepository;
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;

    @BeforeEach
    void beforeEach() {
        basketItemRepository = new BasketItemRepository();
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        promotionService = new PromotionService(basketItemRepository, productRepository, promotionRepository);
    }

    @DisplayName("추가적인 증정 상품을 계산한다")
    @Test
    void 추가적인_증정_상품을_계산한다() {
        promotionRepository.add(PromotionGenerator.makeValid("특별할인", 2, 1));
        productRepository.add(new Product("콜라", 1000, 10, 10, "특별할인"));
        basketItemRepository.add(new BasketItem("콜라", 8));

        List<AdditionalGiftItem> additionalGiftItem = promotionService.findAdditionalGiftItem();

        assertThat(additionalGiftItem)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(new AdditionalGiftItem("콜라", 1)));
    }

    @DisplayName("프로모션 기간이 아닌 상품의 경우 추가적인 증정 상품 계산에서 제외한다")
    @Test
    void 프로모션_기간이_아닌_상품의_경우_추가적인_증정_상품_계산에서_제외한다() {
        promotionRepository.add(PromotionGenerator.makeInvalid("특별할인", 2, 1));
        productRepository.add(new Product("콜라", 1000, 10, 10, "특별할인"));
        basketItemRepository.add(new BasketItem("콜라", 8));

        List<AdditionalGiftItem> additionalGiftItem = promotionService.findAdditionalGiftItem();

        assertThat(additionalGiftItem).isEmpty();
    }

    @DisplayName("상품의 프로모션 수량이 부족하면 추가 증정 상품 계산에서 제외된다")
    @Test
    void 상품의_프로모션_수량이_부족하면_추가_증정_상품_계산에서_제외된다() {
        promotionRepository.add(PromotionGenerator.makeInvalid("특별할인", 2, 1));
        productRepository.add(new Product("콜라", 1000, 10, 8, "특별할인"));
        basketItemRepository.add(new BasketItem("콜라", 8));

        List<AdditionalGiftItem> additionalGiftItem = promotionService.findAdditionalGiftItem();

        assertThat(additionalGiftItem).isEmpty();
    }

    @DisplayName("프로모션 미적용 상품을 계산한다")
    @Test
    void 프로모션_미적용_상품을_계산한다() {
        promotionRepository.add(PromotionGenerator.makeValid("특별할인", 2, 1));
        productRepository.add(new Product("콜라", 1000, 10, 10, "특별할인"));
        basketItemRepository.add(new BasketItem("콜라", 12));

        List<NonPromotableItem> nonPromotableItems = promotionService.findNonPromotableItem();

        assertThat(nonPromotableItems)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(new NonPromotableItem("콜라", 3)));
    }

    @DisplayName("프로모션 기간이 아닌 상품의 경우 프로모션 미적용 상품 계산에서 제외한다")
    @Test
    void 프로모션_기간이_아닌_상품의_경우_프로모션_미적용_상품_계산에서_제외한다() {
        promotionRepository.add(PromotionGenerator.makeInvalid("특별할인", 2, 1));
        productRepository.add(new Product("콜라", 1000, 10, 10, "특별할인"));
        basketItemRepository.add(new BasketItem("콜라", 12));

        List<NonPromotableItem> nonPromotableItems = promotionService.findNonPromotableItem();

        assertThat(nonPromotableItems).isEmpty();
    }
}