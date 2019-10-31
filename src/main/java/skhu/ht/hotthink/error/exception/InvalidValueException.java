package skhu.ht.hotthink.error.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.BusinessException;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidValueException(String message) {
        super(message,ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
