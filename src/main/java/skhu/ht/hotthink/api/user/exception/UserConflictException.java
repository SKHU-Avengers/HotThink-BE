package skhu.ht.hotthink.api.user.exception;

import skhu.ht.hotthink.error.exception.BusinessException;
import skhu.ht.hotthink.error.ErrorCode;

public class UserConflictException extends BusinessException {
    public UserConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
