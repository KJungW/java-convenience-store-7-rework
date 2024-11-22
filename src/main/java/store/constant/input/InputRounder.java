package store.constant.input;

public enum InputRounder {
    SQUARE_BRACKETS_LEFT("["),
    SQUARE_BRACKETS_RIGHT("]");

    private String content;

    InputRounder(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
