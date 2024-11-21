package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.exception_message.DomainExceptionMessage;

class BasketItemTest {

    @DisplayName("장바구니 아이템에 수량을 추가한다")
    @Test
    void 장바구니_아이템에_수량을_추가한다() {
        BasketItem basketItem = new BasketItem("콜라", 10);

        basketItem.addQuantity(5);

        assertThat(basketItem.getQuantity()).isEqualTo(15);
    }

    @DisplayName("장바구니 아이템의 수량을 차감한다")
    @Test
    void 장바구니_아이템의_수량을_차감한다() {
        BasketItem basketItem = new BasketItem("콜라", 10);

        basketItem.subtractQuantity(5);

        assertThat(basketItem.getQuantity()).isEqualTo(5);
    }

    @DisplayName("수량 부족으로 수량 차감이 불가능할 경우 수량 차감시 예외가 발생한다")
    @Test
    void 수량_부족으로_수량_차감이_불가능할_경우_수량_차감시_예외가_발생한다() {
        BasketItem basketItem = new BasketItem("콜라", 10);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> basketItem.subtractQuantity(20))
                .withMessage(DomainExceptionMessage.QUANTITY_SUBTRACTION_IS_IMPOSSIBLE.getMessage());
    }

    @DisplayName("장바구니 아이템을 수량 차감할 수 있는지 체크한다")
    @Test
    void 장바구니_아이템을_수량_차감할_수_있는지_체크한다() {
        BasketItem basketItem = new BasketItem("콜라", 10);

        assertThat(basketItem.checkQuantitySubtractionIsPossible(5)).isTrue();
        assertThat(basketItem.checkQuantitySubtractionIsPossible(20)).isFalse();
    }
}