package skhu.ht.hotthink.api.payment.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.ht.hotthink.api.domain.enums.PayCategory;
import skhu.ht.hotthink.api.domain.enums.PayMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayDTO {
    @Enumerated(EnumType.STRING)
    protected PayMethod payMethod;
}
