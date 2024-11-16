package store.constant.exception_message;

public enum RepositoryExceptionMessage {
    DATA_NOT_EXIST("존재하지 않는 데이터 입니다."),
    DATA_ALREADY_EXIST("이미 존재하는 데이터 입니다."),
    DATA_NOT_FOUND("해당하는 데이터를 찾을 수 없습니다.");

    private String message;

    RepositoryExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
