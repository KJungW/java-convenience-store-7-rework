package store.constant.input;

public enum InputSeparator {
    COMMA(","),
    HYPHEN("-");

    private String content;

    InputSeparator(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
