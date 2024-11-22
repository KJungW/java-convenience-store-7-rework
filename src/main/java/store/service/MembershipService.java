package store.service;

import java.util.List;
import store.dto.GiftedItem;
import store.dto.PurchasedItem;
import store.dto.Receipt;

public class MembershipService {

    private static final double DISCOUNT_RATE = 0.3d;
    private static final int MAXIMUM_DISCOUNT_AMOUNT = 8000;

    public Receipt calculateMembershipDiscount(Receipt receipt) {
        List<PurchasedItem> nonPromotableItems = calculateNonPromotablePurchasedItem(receipt);
        int originPrice = nonPromotableItems.stream().mapToInt(PurchasedItem::calculateFullPrice).sum();
        int discountAmount = (int) (originPrice * DISCOUNT_RATE);
        if (discountAmount <= MAXIMUM_DISCOUNT_AMOUNT) {
            receipt.applyMembershipDiscount(discountAmount);
            return receipt;
        }
        receipt.applyMembershipDiscount(MAXIMUM_DISCOUNT_AMOUNT);
        return receipt;
    }

    public List<PurchasedItem> calculateNonPromotablePurchasedItem(Receipt receipt) {
        List<PurchasedItem> purchasedItems = receipt.getPurchasedItems();
        List<GiftedItem> giftedItems = receipt.getGiftedItems();
        for (GiftedItem giftedItem : giftedItems) {
            purchasedItems = purchasedItems.stream()
                    .filter(purchasedItem -> !purchasedItem.getProductName().equals(giftedItem.getProductName()))
                    .toList();
        }
        return purchasedItems;
    }
}
