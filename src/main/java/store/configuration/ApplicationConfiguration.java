package store.configuration;

import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.InitialSettingService;

public class ApplicationConfiguration {

    private final PromotionRepository promotionRepository
            = new PromotionRepository();
    private final ProductRepository productRepository
            = new ProductRepository();
    private final BasketItemRepository basketItemRepository
            = new BasketItemRepository();
    private final InitialSettingService initialSettingService
            = new InitialSettingService(productRepository, promotionRepository);

    public InitialSettingService getInitialSettingService() {
        return initialSettingService;
    }
}
