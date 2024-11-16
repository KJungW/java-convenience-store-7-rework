package store;

import store.configuration.ApplicationConfiguration;
import store.controller.ConvenienceStoreController;

public class Application {

    public static void main(String[] args) {
        ApplicationConfiguration configuration = new ApplicationConfiguration();
        ConvenienceStoreController controller = new ConvenienceStoreController(configuration);
        controller.run();
    }
}
