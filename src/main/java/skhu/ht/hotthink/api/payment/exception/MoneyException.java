package skhu.ht.hotthink.api.payment.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.BusinessException;

public class MoneyException extends BusinessException {
    public MoneyException(String message) {
        super(message, ErrorCode.NOT_ENOUGH_MONEY);
    }

    public MoneyException() {
        super(ErrorCode.NOT_ENOUGH_MONEY);
    }
}
