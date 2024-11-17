package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.exception_message.ServiceExceptionMessage;
import store.domain.BasketItem;
import store.domain.Product;
import store.repository.BasketItemRepository;
import store.repository.ProductRepository;

class BasketServiceTest {

    private BasketService basketService;
    private ProductRepository productRepository;
    private BasketItemRepository basketItemRepository;

    @BeforeEach
    void beforeEach() {
        productRepository = new ProductRepository();
        basketItemRepository = new BasketItemRepository();
        basketService = new BasketService(basketItemRepository, productRepository);
    }

    @DisplayName("장바구니에 아이템을 추가할 수 있다")
    @Test
    void 장바구니에_아이템을_추가할_수_있다() {
        productRepository.add(new Product("콜라", 3000, 10, "탄산2+1"));
        productRepository.add(new Product("사이다", 2000, 10));
        List<BasketItem> basketItems = List.of(
                new BasketItem("콜라", 10),
                new BasketItem("사이다", 5));

        basketService.addBasketItems(basketItems);

        checkAddedBasketItems(basketItems);
    }

    void checkAddedBasketItems(List<BasketItem> expectedAddedBasketItems) {
        List<BasketItem> actualAddedBasketItems = basketItemRepository.findAll();
        assertThat(actualAddedBasketItems)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedAddedBasketItems);
    }

    @DisplayName("상품의 수량이 부족하면 장바구니에 아이템을 추가할 수 없다")
    @Test
    void 상품의_수량이_부족하면_장바구니에_아이템을_추가할_수_없다() {
        productRepository.add(new Product("콜라", 3000, 10, "탄산2+1"));
        List<BasketItem> basketItems = List.of(new BasketItem("콜라", 15));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> basketService.addBasketItems(basketItems))
                .withMessage(ServiceExceptionMessage.NOT_ENOUGH_PRODUCT_QUANTITY.getMessage());
    }

    @DisplayName("상품이 존재하지 않으면 장바구니에 아이템을 추가할 수 없다")
    @Test
    void 상품이_존재하지_않으면_장바구니에_아이템을_추가할_수_없다() {
        productRepository.add(new Product("사이다", 3000, 10, "탄산2+1"));
        List<BasketItem> basketItems = List.of(new BasketItem("콜라", 15));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> basketService.addBasketItems(basketItems))
                .withMessage(ServiceExceptionMessage.NOT_EXIST_PRODUCT.getMessage());
    }

    @DisplayName("기존 장바구니 아이템의 수량을 추가할 수 있다")
    @Test
    void 기존_장바구니_아이템의_수량을_추가할_수_있다() {
        productRepository.add(new Product("콜라", 3000, 10, "탄산2+1"));
        List<BasketItem> basketItems = List.of(new BasketItem("콜라", 5));
        basketService.addBasketItems(basketItems);

        basketService.addBasketItemQuantity("콜라", 5);

        BasketItem updatedBasketItem = basketItemRepository.find("콜라");
        assertThat(updatedBasketItem.getQuantity()).isEqualTo(10);
    }

    @DisplayName("존재하지 않는 장바구니 아이템의 수량은 추가할 수 없다")
    @Test
    void 존재하지_않는_장바구니_아이템의_수량은_추가할_수_없다() {
        productRepository.add(new Product("사이다", 3000, 10, "탄산2+1"));
        List<BasketItem> basketItems = List.of(new BasketItem("사이다", 5));
        basketService.addBasketItems(basketItems);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> basketService.addBasketItemQuantity("콜라", 5))
                .withMessage(ServiceExceptionMessage.NOT_EXIST_BASKET_ITEM.getMessage());
    }

    @DisplayName("상품의 재고가 부족하도록 장바구니 아이템의 수량을 추가할 수 없다")
    @Test
    void 상품의_재고가_부족하도록_장바구니_아이템의_수량을_추가할_수_없다() {
        productRepository.add(new Product("콜라", 3000, 10, "탄산2+1"));
        List<BasketItem> basketItems = List.of(new BasketItem("콜라", 5));
        basketService.addBasketItems(basketItems);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> basketService.addBasketItemQuantity("콜라", 6))
                .withMessage(ServiceExceptionMessage.NOT_ENOUGH_PRODUCT_QUANTITY.getMessage());
    }
}