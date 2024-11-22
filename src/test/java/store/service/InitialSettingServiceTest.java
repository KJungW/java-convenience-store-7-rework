package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.InitialSettingFileName;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class InitialSettingServiceTest {

    private static final String INITIAL_PRODUCT_FILE_NAME = "src/test/resources/products.md";
    private static final String INITIAL_PROMOTION_FILE_NAME = "src/test/resources/promotions.md";

    private InitialSettingService initialSettingService;
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;

    @BeforeEach
    void beforeEach() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        initialSettingService = new InitialSettingService(productRepository, promotionRepository);
    }

    @DisplayName("편의점의 초기 프로모션을 세팅할 수 있다")
    @Test
    public void 편의점의_초기_프로모션을_세팅할_수_있다() {
        initialSettingService.initialize(INITIAL_PRODUCT_FILE_NAME, INITIAL_PROMOTION_FILE_NAME);

        checkAllPromotionsAreSaved();
        checkPromotionDetailIsSaved();
    }

    void checkAllPromotionsAreSaved() {
        List<Promotion> allPromotions = promotionRepository.findAll();
        List<String> allPromotionNames = allPromotions.stream().map(Promotion::getName).toList();
        assertThat(allPromotionNames).contains("탄산2+1", "MD추천상품", "반짝할인");
    }

    void checkPromotionDetailIsSaved() {
        Promotion promotion = promotionRepository.find("탄산2+1");
        LocalDateTime expectedStartDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2024, 12, 31, 0, 0, 0);
        assertThat(promotion)
                .usingRecursiveComparison()
                .isEqualTo(new Promotion("탄산2+1", 2, 1, expectedStartDate, expectedEndDate));
    }

    @DisplayName("편의점의 초기 상품을 세팅할 수 있다")
    @Test
    public void 편의점의_초기_제품을_세팅할_수_있다() {
        initialSettingService.initialize(
                InitialSettingFileName.INITIAL_PRODUCT.getName(), InitialSettingFileName.INITIAL_PROMOTION.getName());

        checkAllProductsAreSaved();
        checkProductDetailIsSaved();
    }

    void checkAllProductsAreSaved() {
        List<Product> allProduct = productRepository.findAll();
        List<String> allProductNames = allProduct.stream().map(Product::getName).toList();
        assertThat(allProductNames).contains("콜라", "사이다", "탄산수", "물");
    }

    void checkProductDetailIsSaved() {
        Product product = productRepository.find("콜라");
        assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(new Product("콜라", 1000, 10, 10, "탄산2+1"));
    }
}