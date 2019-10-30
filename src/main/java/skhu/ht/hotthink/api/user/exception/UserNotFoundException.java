package skhu.ht.hotthink.api.user.exception;

import skhu.ht.hotthink.error.exception.BusinessException;
import skhu.ht.hotthink.error.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
