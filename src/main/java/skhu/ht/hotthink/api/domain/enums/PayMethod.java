package skhu.ht.hotthink.api.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum PayMethod {
    CREDIT_CARD,
    CASH;

    @JsonCreator
    public static PayMethod fromString(String symbol){
        return PayMethod.valueOf(symbol);
    }
}
