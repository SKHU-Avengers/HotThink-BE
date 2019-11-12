package skhu.ht.hotthink.api.payment.model;

import java.util.Date;

public enum Period {
    ONE(1),
    THREE(3),
    SIX(6),
    TWELVE(12);
    private Integer period;

    Period(Integer period) {
        this.period = period;
    }
}
