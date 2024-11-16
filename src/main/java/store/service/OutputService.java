package store.service;

import store.view.OutputView;

public class OutputService {

    private final OutputView outputView;

    public OutputService(OutputView outputView) {
        this.outputView = outputView;
    }

    public void printWelcomeGreeting() {
        outputView.printContent("안녕하세요. W편의점입니다.");
    }
}
