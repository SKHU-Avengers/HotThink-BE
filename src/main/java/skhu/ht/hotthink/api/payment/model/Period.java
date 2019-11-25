package skhu.ht.hotthink.api.payment.model;

import java.util.Date;

public enum Period {
    ONE(1),
    THREE(3),
    SIX(6),
    TWELVE(12);
    private Integer period;
    Period(){}//DEFAULT CONSTRUCTOR
    Period(Integer period) {
        this.period = period;
    }
    public int getPeriod(){ return this.period; }
    public int getPrice(int price){ return price*period; }
}
