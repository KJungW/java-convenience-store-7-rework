package store.controller;

import java.util.List;
import store.configuration.ApplicationConfiguration;
import store.constant.InitialSettingFileName;
import store.domain.BasketItem;
import store.dto.AdditionalGiftItem;
import store.dto.NonPromotableItem;
import store.service.BasketService;
import store.service.InitialSettingService;
import store.service.InputService;
import store.service.OutputService;
import store.service.PromotionService;

public class ConvenienceStoreController {

    private final InitialSettingService initialSettingService;
    private final OutputService outputService;
    private final InputService inputService;
    private final BasketService basketService;
    private final PromotionService promotionService;

    public ConvenienceStoreController(ApplicationConfiguration configuration) {
        this.initialSettingService = configuration.getInitialSettingService();
        this.outputService = configuration.getOutputService();
        this.inputService = configuration.getInputService();
        this.basketService = configuration.getBasketService();
        this.promotionService = configuration.getPromotionService();
        this.initialSettingService.initialize(
                InitialSettingFileName.INITIAL_PRODUCT.getName(),
                InitialSettingFileName.INITIAL_PROMOTION.getName());
    }

    public void run() {
        outputService.printWelcomeGreeting();
        outputService.printProductGuideMessage();
        outputService.printProductsInStore();
        List<BasketItem> basketItems = inputService.inputBasketItems();
        basketService.addBasketItems(basketItems);
        addAdditionalGiftItem();
        processNonPromotableItem();
        boolean isMembershipDiscountAccept = inputService.inputMembershipDiscountAcceptance();
    }

    private void addAdditionalGiftItem() {
        List<AdditionalGiftItem> giftItems = promotionService.findAdditionalGiftItem();
        for (AdditionalGiftItem item : giftItems) {
            boolean isAcceptance = inputService.inputAdditionalGiftItemAcceptance(item);
            if (isAcceptance) {
                basketService.addBasketItemQuantity(item.getProductName(), item.getQuantity());
            }
        }
    }

    private void processNonPromotableItem() {
        List<NonPromotableItem> nonPromotableItems = promotionService.findNonPromotableItem();
        for (NonPromotableItem item : nonPromotableItems) {
            boolean isAcceptance = inputService.inputNonPromotableItemAcceptance(item);
            if (!isAcceptance) {
                basketService.subtractBasketItemQuantity(item.getProductName(), item.getQuantity());
            }
        }
    }
}
