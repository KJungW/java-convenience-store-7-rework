package store.dto;

import java.util.List;

public class Receipt {

    private final List<PurchasedItem> purchasedItems;
    private final List<GiftedItem> giftedItems;
    private int membershipDiscount;

    public Receipt(List<PurchasedItem> purchasedItems, List<GiftedItem> giftedItems) {
        this.purchasedItems = purchasedItems;
        this.giftedItems = giftedItems;
        this.membershipDiscount = 0;
    }

    public void applyMembershipDiscount(int discountAmount) {
        this.membershipDiscount = discountAmount;
    }

    public int calculatePurchasePrice() {
        return purchasedItems.stream().mapToInt(PurchasedItem::calculateFullPrice).sum();
    }

    public int calculateGiftPrice() {
        return giftedItems.stream().mapToInt(GiftedItem::calculateFullPrice).sum();
    }

    public int calculatePriceToPay() {
        int purchasePrice = calculatePurchasePrice();
        int giftPrice = calculateGiftPrice();
        return purchasePrice - giftPrice - membershipDiscount;
    }

    public List<PurchasedItem> getPurchasedItems() {
        return purchasedItems.stream()
                .map(PurchasedItem::copy)
                .toList();
    }

    public List<GiftedItem> getGiftedItems() {
        return giftedItems.stream()
                .map(GiftedItem::copy)
                .toList();
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }
}
