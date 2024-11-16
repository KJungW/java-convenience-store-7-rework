package store.controller;

import store.configuration.ApplicationConfiguration;
import store.constant.InitialSettingFileName;
import store.service.InitialSettingService;
import store.service.OutputService;

public class ConvenienceStoreController {

    private final InitialSettingService initialSettingService;
    private final OutputService outputService;

    public ConvenienceStoreController(ApplicationConfiguration configuration) {
        this.initialSettingService = configuration.getInitialSettingService();
        this.outputService = configuration.getOutputService();

        this.initialSettingService.initialize(
                InitialSettingFileName.INITIAL_PRODUCT.getName(),
                InitialSettingFileName.INITIAL_PROMOTION.getName());
    }

    public void run() {
        outputService.printWelcomeGreeting();
    }
}
