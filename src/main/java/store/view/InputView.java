package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.input_output_message.InputGuideMessage;

public class InputView {

    public String inputBasketItems() {
        System.out.println(InputGuideMessage.BASKET_ITEMS_INPUT.getMessage());
        return Console.readLine();
    }
}
