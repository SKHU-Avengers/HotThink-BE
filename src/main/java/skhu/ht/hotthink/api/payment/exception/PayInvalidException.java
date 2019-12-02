package skhu.ht.hotthink.api.payment.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.InvalidValueException;

public class PayInvalidException extends InvalidValueException {

    public PayInvalidException(String message) {
        super(message, ErrorCode.INVALID_TYPE_VALUE);
    }

    public PayInvalidException() {
        super(ErrorCode.INVALID_TYPE_VALUE);
    }
}
