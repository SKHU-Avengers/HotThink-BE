package skhu.ht.hotthink.api.user.exception;

import skhu.ht.hotthink.error.exception.BusinessException;
import skhu.ht.hotthink.error.ErrorCode;

public class UserConflictException extends BusinessException {
    public UserConflictException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
    public UserConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
