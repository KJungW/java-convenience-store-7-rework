package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.constant.exception_message.DomainExceptionMessage;

class ProductTest {

    @DisplayName("프로모션 상품이 존재하는지 체크할 수 있다")
    @ParameterizedTest
    @MethodSource
    void 프로모션_상품이_존재하는지_체크할_수_있다(Product product, boolean expectedIsPromoted) {
        assertThat(product.checkIsPromoted()).isEqualTo(expectedIsPromoted);
    }

    static Stream<Arguments> 프로모션_상품이_존재하는지_체크할_수_있다() {
        return Stream.of(
                Arguments.of(new Product("콜라", 1000, 10, "특별할인"), true),
                Arguments.of(new Product("콜라", 1000, 10), false)
        );
    }

    @DisplayName("일반 수량을 추가할 수 있다")
    @Test
    void 일반_수량을_추가할_수_있다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        product.addCommonQuantity(10);

        assertThat(product.getCommonQuantity()).isEqualTo(20);
    }

    @DisplayName("일반 수량을 차감할 수 있다")
    @Test
    void 일반_수량을_차감할_수_있다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        product.subtractCommonQuantity(10);

        assertThat(product.getCommonQuantity()).isEqualTo(0);
        assertThat(product.getPromotionQuantity()).isEqualTo(10);
    }

    @DisplayName("일반 수량이 부족할 경우 일발 수량 차감시 예외가 발생한다")
    @Test
    void 일반_수량이_부족할_경우_일발_수량_차감시_예외가_발생한다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> product.subtractCommonQuantity(20))
                .withMessage(DomainExceptionMessage.QUANTITY_SUBTRACTION_IS_IMPOSSIBLE.getMessage());
    }

    @DisplayName("프로모션 수량을 차감할 수 있다")
    @Test
    void 프로모션_수량을_차감할_수_있다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        product.subtractPromotionQuantity(10);

        assertThat(product.getCommonQuantity()).isEqualTo(10);
        assertThat(product.getPromotionQuantity()).isEqualTo(0);
    }

    @DisplayName("프로모션 수량이 부족할 경우 프로모션 수량 차감시 예외가 발생한다")
    @Test
    void 프로모션_수량이_부족할_경우_프로모션_수량_차감시_예외가_발생한다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> product.subtractPromotionQuantity(20))
                .withMessage(DomainExceptionMessage.QUANTITY_SUBTRACTION_IS_IMPOSSIBLE.getMessage());
    }

    @DisplayName("프로모션 수량과 일반 수량을 합한 전체 수량이 충분한지 체크한다")
    @Test
    void 프로모션_수량과_일반_수량을_합한_전체_수량이_충분한지_체크한다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        assertThat(product.isEnoughQuantity(20)).isTrue();
        assertThat(product.isEnoughQuantity(21)).isFalse();
    }


    @DisplayName("일반 수량이 충분한지 체크한다")
    @Test
    void 일반_수량이_충분한지_체크한다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        assertThat(product.isEnoughCommonQuantity(10)).isTrue();
        assertThat(product.isEnoughCommonQuantity(11)).isFalse();
    }


    @DisplayName("프로모션 수량이 충분한지 체크한다")
    @Test
    void 프로모션_수량이_충분한지_체크한다() {
        Product product = new Product("콜라", 1000, 10, 10, "특별할인");

        assertThat(product.isEnoughPromotionQuantity(10)).isTrue();
        assertThat(product.isEnoughPromotionQuantity(11)).isFalse();
    }
}