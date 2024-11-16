package store.domain;

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

    public String getName() {
        return name;
    }

    public Promotion copy() {
        return new Promotion(name, purchaseCount, giftCount, startDate, endDate);
    }
}
