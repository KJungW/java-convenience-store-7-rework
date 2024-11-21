package store.view;

import store.constant.input_output_message.OutputMessage;

public class OutputView {

    public void printBlankLine() {
        System.out.println();
    }

    public void printContent(String content) {
        System.out.println(content);
    }

    public void printError(String message) {
        String content = OutputMessage.ERROR_MESSAGE_PREFIX.getMessage() + message;
        System.out.println(content);
    }
}
