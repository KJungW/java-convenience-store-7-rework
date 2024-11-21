package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.generator.PromotionGenerator;

class PromotionTest {

    @DisplayName("추가적인 증정 상품 개수를 계산한다")
    @ParameterizedTest
    @CsvSource(value = {"0,0", "1,0", "2,1", "3,0", "4,0", "5,1", "11,1"})
    void 추가적인_증정_상품_개수를_계산할_수_있다(int quantity, int expectedAdditionalGiftCount) {
        Promotion promotion = PromotionGenerator.makeInvalid("특별할인", 2, 1);

        int actualAdditionalGiftCount = promotion.calculateAdditionalGiftCount(quantity);

        assertThat(actualAdditionalGiftCount).isEqualTo(expectedAdditionalGiftCount);
    }

    @DisplayName("프로모션이 적용되지 않는 상품 개수를 계산한다")
    @ParameterizedTest
    @CsvSource(value = {"0,0", "1,1", "2,2", "3,0", "4,1", "5,2", "11,5"})
    void 프로모션이_적용되지_않는_상품_개수를_계산한다(int quantity, int expectedNotPromotableCount) {
        Promotion promotion = PromotionGenerator.makeInvalid("특별할인", 2, 1);

        int actualNotPromotableCount = promotion.calculateNonPromotableItemCount(quantity, 6);

        assertThat(actualNotPromotableCount).isEqualTo(expectedNotPromotableCount);
    }

    @DisplayName("증정 상품 개수를 계산한다")
    @ParameterizedTest
    @CsvSource(value = {"0,0", "1,0", "2,0", "3,1", "4,1", "5,1", "11,2"})
    void 증정_상품_개수를_계산한다(int quantity, int expectedGiftCount) {
        Promotion promotion = PromotionGenerator.makeInvalid("특별할인", 2, 1);

        int actualGiftCount = promotion.calculateGiftedItemCount(quantity, 6);

        assertThat(actualGiftCount).isEqualTo(expectedGiftCount);
    }
}