package store.controller;

import store.configuration.ApplicationConfiguration;
import store.constant.InitialSettingFileName;
import store.service.InitialSettingService;

public class ConvenienceStoreController {

    private final InitialSettingService initialSettingService;

    public ConvenienceStoreController(ApplicationConfiguration configuration) {
        this.initialSettingService = configuration.getInitialSettingService();
        this.initialSettingService.initialize(
                InitialSettingFileName.INITIAL_PRODUCT.getName(),
                InitialSettingFileName.INITIAL_PROMOTION.getName());
    }

    public void run() {

    }
}
