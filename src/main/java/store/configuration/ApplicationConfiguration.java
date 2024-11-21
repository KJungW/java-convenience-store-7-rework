package store.configuration;

import store.repository.BasketItemRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.BasketService;
import store.service.InitialSettingService;
import store.service.InputService;
import store.service.MembershipService;
import store.service.OutputService;
import store.service.PromotionService;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

public class ApplicationConfiguration {

    private final OutputView outputView
            = new OutputView();
    private final InputView inputView
            = new InputView();
    private final PromotionRepository promotionRepository
            = new PromotionRepository();
    private final ProductRepository productRepository
            = new ProductRepository();
    private final BasketItemRepository basketItemRepository
            = new BasketItemRepository();
    private final InitialSettingService initialSettingService
            = new InitialSettingService(productRepository, promotionRepository);
    private final BasketService basketService
            = new BasketService(basketItemRepository, productRepository);
    private final PromotionService promotionService
            = new PromotionService(basketItemRepository, productRepository, promotionRepository);
    private final PurchaseService purchaseService
            = new PurchaseService(basketItemRepository, productRepository, promotionRepository);
    private final MembershipService membershipService
            = new MembershipService();
    private final OutputService outputService
            = new OutputService(outputView, productRepository);
    private final InputService inputService
            = new InputService(inputView, outputView, basketService);

    public InitialSettingService getInitialSettingService() {
        return initialSettingService;
    }

    public OutputService getOutputService() {
        return outputService;
    }

    public InputService getInputService() {
        return inputService;
    }

    public BasketService getBasketService() {
        return basketService;
    }

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public PurchaseService getPurchaseService() {
        return purchaseService;
    }

    public MembershipService getMembershipService() {
        return membershipService;
    }
}
