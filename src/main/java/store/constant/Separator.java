package store.constant;

public enum Separator {
    COMMA(","),
    HYPHEN("-");

    private String content;

    Separator(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
