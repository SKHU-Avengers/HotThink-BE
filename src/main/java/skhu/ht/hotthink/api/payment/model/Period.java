package skhu.ht.hotthink.api.payment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonCreator
    public static Period fromString(String symbol){
        return Period.valueOf(symbol);
    }

    @JsonValue
    public int getPeriod(){ return this.period; }
    public int caculatePrice(int price){
        if(period != null)
            return price*period;
        else return 0;
    }
}
