package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constant.input_output_message.InputGuideMessage;
import store.dto.AdditionalGiftItem;
import store.dto.NonPromotableItem;

public class InputView {

    public String inputBasketItems() {
        System.out.println(InputGuideMessage.BASKET_ITEMS_INPUT.getMessage());
        return Console.readLine();
    }

    public String inputAdditionalGiftItemAcceptance(AdditionalGiftItem giftItem) {
        String guideMessage = String.format(InputGuideMessage.ADDITIONAL_GIFT_ITEM_ACCEPTANCE_INPUT.getMessage(),
                giftItem.getProductName(), giftItem.getQuantity());
        System.out.println(guideMessage);
        return Console.readLine();
    }

    public String inputNonPromotableItemAcceptance(NonPromotableItem item) {
        String guideMessage = String.format(InputGuideMessage.NOT_PROMOTABLE_ITEM_ACCEPTANCE_INPUT.getMessage(),
                item.getProductName(), item.getQuantity());
        System.out.println(guideMessage);
        return Console.readLine();
    }
}
