package store.constant;

public enum InitialSettingFileName {
    INITIAL_PRODUCT("src/main/resources/products.md"),
    INITIAL_PROMOTION("src/main/resources/promotions.md");

    private String name;

    InitialSettingFileName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
