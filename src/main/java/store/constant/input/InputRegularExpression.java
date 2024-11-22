package store.constant.input;

public enum InputRegularExpression {
    BASKET_ITEM_INPUT_FORMAT("^\\[[가-힣a-zA-Z0-9]+-\\d+\\](,\\[[가-힣a-zA-Z0-9]+-\\d+\\])*$"),
    YES_OR_NO_INPUT_FORMAT("^[Y|N]$");

    private final String expression;

    InputRegularExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
