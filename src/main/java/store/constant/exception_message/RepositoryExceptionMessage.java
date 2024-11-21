package store.constant.exception_message;

public enum RepositoryExceptionMessage {
    DATA_DOES_NOT_EXIST("존재하지 않는 데이터 입니다."),
    DATA_ALREADY_EXIST("이미 존재하는 데이터 입니다.");

    private String message;

    RepositoryExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
