package store.controller;

import java.util.List;
import store.configuration.ApplicationConfiguration;
import store.constant.InitialSettingFileName;
import store.domain.BasketItem;
import store.service.InitialSettingService;
import store.service.InputService;
import store.service.OutputService;

public class ConvenienceStoreController {

    private final InitialSettingService initialSettingService;
    private final OutputService outputService;
    private final InputService inputService;

    public ConvenienceStoreController(ApplicationConfiguration configuration) {
        this.initialSettingService = configuration.getInitialSettingService();
        this.outputService = configuration.getOutputService();
        this.inputService = configuration.getInputService();

        this.initialSettingService.initialize(
                InitialSettingFileName.INITIAL_PRODUCT.getName(),
                InitialSettingFileName.INITIAL_PROMOTION.getName());
    }

    public void run() {
        outputService.printWelcomeGreeting();
        outputService.printProductGuideMessage();
        outputService.printProductsInStore();
        List<BasketItem> basketItems = inputService.inputBasketItems();
    }
}
