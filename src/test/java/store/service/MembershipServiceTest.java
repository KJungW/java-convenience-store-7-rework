package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.GiftedItem;
import store.dto.PurchasedItem;
import store.dto.Receipt;

class MembershipServiceTest {

    private static final int MAXIMUM_DISCOUNT_AMOUNT = 8000;

    MembershipService membershipService;

    @BeforeEach
    void beforeEach() {
        membershipService = new MembershipService();
    }

    @DisplayName("프로모션이 적용되지 않은 상품에 멤버십 할인을 적용한다")
    @Test
    void 프로모션이_적용되지_않은_상품에_멤버십_할인을_적용한다() {
        Receipt givenReceipt = setReceipt();

        Receipt resultReceipt = membershipService.calculateMembershipDiscount(givenReceipt);

        assertThat(resultReceipt.getMembershipDiscount()).isEqualTo(3000);
    }

    private Receipt setReceipt() {
        List<PurchasedItem> purchasedItems = List.of(
                new PurchasedItem("콜라", 1000, 5),
                new PurchasedItem("사이다", 1000, 5),
                new PurchasedItem("웰치스", 1000, 5));
        List<GiftedItem> giftedItems = List.of(
                new GiftedItem("콜라", 1000, 3));
        return new Receipt(purchasedItems, giftedItems);
    }

    @DisplayName("멤버십 할인의 한도는 정해져있다")
    @Test
    void 멤버십_할인의_한도는_정해져있다() {
        List<PurchasedItem> purchasedItems = List.of(new PurchasedItem("콜라", 100000, 5));
        Receipt givenReceipt = new Receipt(purchasedItems, Collections.emptyList());

        Receipt resultReceipt = membershipService.calculateMembershipDiscount(givenReceipt);

        assertThat(resultReceipt.getMembershipDiscount()).isEqualTo(MAXIMUM_DISCOUNT_AMOUNT);
    }
}