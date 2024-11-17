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

    public List<PurchasedItem> getPurchasedItems() {
        return purchasedItems;
    }

    public List<GiftedItem> getGiftedItems() {
        return giftedItems;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }
}
