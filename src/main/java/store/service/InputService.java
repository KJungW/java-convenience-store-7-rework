package store.service;

import java.util.List;
import store.constant.DefaultValue;
import store.constant.input.InputRegularExpression;
import store.constant.input.InputRounder;
import store.constant.input.InputSeparator;
import store.constant.input.YesOrNo;
import store.constant.parsing.BasketItemsParsingFormat;
import store.constant.view_message.InputExceptionMessage;
import store.domain.BasketItem;
import store.dto.AdditionalGiftItem;
import store.dto.NonPromotableItem;
import store.exception.InputException;
import store.view.InputView;
import store.view.OutputView;

public class InputService {

    private final InputView inputView;
    private final OutputView outputView;
    private final BasketService basketService;

    public InputService(InputView inputView, OutputView outputView, BasketService basketService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.basketService = basketService;
    }

    public List<BasketItem> inputBasketItems() {
        while (true) {
            try {
                return tryInputBasketItems();
            } catch (InputException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalArgumentException exception) {
                outputView.printError(InputExceptionMessage.INPUT_IS_INVALID.getMessage());
            }
        }
    }

    public boolean inputAdditionalGiftItemAcceptance(AdditionalGiftItem giftItem) {
        while (true) {
            try {
                return tryInputAdditionalGiftItemAcceptance(giftItem);
            } catch (InputException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalArgumentException exception) {
                outputView.printError(InputExceptionMessage.INPUT_IS_INVALID.getMessage());
            }
        }
    }

    public boolean inputNonPromotableItemAcceptance(NonPromotableItem promotableItem) {
        while (true) {
            try {
                return tryInputNonPromotableItemAcceptance(promotableItem);
            } catch (InputException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalArgumentException exception) {
                outputView.printError(InputExceptionMessage.INPUT_IS_INVALID.getMessage());
            }
        }
    }

    public boolean inputMembershipDiscountAcceptance() {
        while (true) {
            try {
                return tryInputMembershipDiscountAcceptance();
            } catch (InputException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalArgumentException exception) {
                outputView.printError(InputExceptionMessage.INPUT_IS_INVALID.getMessage());
            }
        }
    }

    public boolean inputShoppingContinuation() {
        while (true) {
            try {
                return tryInputShoppingContinuation();
            } catch (InputException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalArgumentException exception) {
                outputView.printError(InputExceptionMessage.INPUT_IS_INVALID.getMessage());
            }
        }
    }

    private List<BasketItem> tryInputBasketItems() {
        String rawInput = inputView.inputBasketItems();
        validateRawInputBasketItems(rawInput);
        List<BasketItem> basketItems = parseBasketItems(rawInput);
        validateInputBasketItems(basketItems);
        return basketItems;
    }

    private boolean tryInputAdditionalGiftItemAcceptance(AdditionalGiftItem giftItem) {
        String rawInput = inputView.inputAdditionalGiftItemAcceptance(giftItem);
        validateYesOrNo(rawInput);
        return parseYesOrNo(rawInput);
    }

    private boolean tryInputNonPromotableItemAcceptance(NonPromotableItem promotableItem) {
        String rawInput = inputView.inputNonPromotableItemAcceptance(promotableItem);
        validateYesOrNo(rawInput);
        return parseYesOrNo(rawInput);
    }

    private boolean tryInputMembershipDiscountAcceptance() {
        String rawInput = inputView.inputMembershipDiscountAcceptance();
        validateYesOrNo(rawInput);
        return parseYesOrNo(rawInput);
    }

    private boolean tryInputShoppingContinuation() {
        String rawInput = inputView.inputShoppingContinuation();
        validateYesOrNo(rawInput);
        return parseYesOrNo(rawInput);
    }

    private void validateRawInputBasketItems(String rawInput) {
        if (!rawInput.matches(InputRegularExpression.BASKET_ITEM_INPUT_FORMAT.getExpression())) {
            throw new InputException(InputExceptionMessage.INPUT_IS_WRONG_FORMAT.getMessage());
        }
    }

    private void validateInputBasketItems(List<BasketItem> basketItems) {
        if (basketItems.stream().noneMatch(basketService::checkProductExistenceForBasketItem)) {
            throw new InputException(InputExceptionMessage.PRODUCT_DOSE_NOT_EXIST.getMessage());
        }
        if (basketItems.stream().noneMatch(basketService::checkEnoughQuantityForBasketItem)) {
            throw new InputException(InputExceptionMessage.PRODUCT_QUANTITY_DOES_NOT_ENOUGH.getMessage());
        }
    }

    public void validateYesOrNo(String input) {
        if (!input.matches(InputRegularExpression.YES_OR_NO_INPUT_FORMAT.getExpression())) {
            throw new InputException(InputExceptionMessage.INPUT_IS_NOT_YES_OR_NO.getMessage());
        }
    }

    private BasketItem parseBasketItem(String input) {
        input = input.replace(InputRounder.SQUARE_BRACKETS_LEFT.getContent(), DefaultValue.EMPTY_TEXT);
        input = input.replace(InputRounder.SQUARE_BRACKETS_RIGHT.getContent(), DefaultValue.EMPTY_TEXT);
        List<String> basketItemParts = List.of(input.split(InputSeparator.HYPHEN.getContent()));
        String name = basketItemParts.get(BasketItemsParsingFormat.NAME.getIndex());
        int quantity = Integer.parseInt(basketItemParts.get(BasketItemsParsingFormat.QUANTITY.getIndex()));
        return new BasketItem(name, quantity);
    }

    private List<BasketItem> parseBasketItems(String input) {
        List<String> rawBasketItems = List.of(input.split(InputSeparator.COMMA.getContent()));
        return rawBasketItems.stream()
                .map(this::parseBasketItem)
                .toList();
    }

    private boolean parseYesOrNo(String input) {
        return YesOrNo.valueOf(input).getContent();
    }
}
