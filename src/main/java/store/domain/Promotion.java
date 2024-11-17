package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class Promotion {

    private String name;
    private int purchaseCount;
    private int giftCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(
            String name,
            int purchaseCount,
            int giftCount,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.giftCount = giftCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int calculateAdditionalGiftCount(int quantity) {
        int restQuantity = quantity % (purchaseCount + giftCount);
        if (restQuantity == purchaseCount) {
            return giftCount;
        }
        return 0;
    }

    public boolean isAvailable() {
        return startDate.isBefore(DateTimes.now()) && endDate.isAfter(DateTimes.now());
    }

    public String getName() {
        return name;
    }

    public Promotion copy() {
        return new Promotion(name, purchaseCount, giftCount, startDate, endDate);
    }
}
