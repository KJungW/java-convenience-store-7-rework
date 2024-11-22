package store.constant.input;

public enum YesOrNo {
    Y(true),
    N(false);

    private boolean content;

    YesOrNo(boolean content) {
        this.content = content;
    }

    public boolean getContent() {
        return content;
    }
}
