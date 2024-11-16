package store.service;

import java.util.List;
import store.constant.Separator;
import store.constant.input.InputRounder;
import store.domain.BasketItem;
import store.view.InputView;

public class InputService {

    private final InputView inputView;

    public InputService(InputView inputView) {
        this.inputView = inputView;
    }

    public List<BasketItem> inputBasketItems() {
        String rawInput = inputView.inputBasketItems();
        return parseBasketItems(rawInput);
    }

    private List<BasketItem> parseBasketItems(String input) {
        List<String> rawBasketItems = List.of(input.split(Separator.COMMA.getContent()));
        return rawBasketItems.stream()
                .map(this::parseBasketItem)
                .toList();
    }

    private BasketItem parseBasketItem(String input) {
        input = input.replace(InputRounder.SQUARE_BRACKETS_LEFT.getContent(), "");
        input = input.replace(InputRounder.SQUARE_BRACKETS_RIGHT.getContent(), "");
        List<String> basketItemParts = List.of(input.split(Separator.HYPHEN.getContent()));
        String name = basketItemParts.get(0);
        int quantity = Integer.parseInt(basketItemParts.get(1));
        return new BasketItem(name, quantity);
    }
}
