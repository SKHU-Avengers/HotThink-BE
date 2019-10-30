package skhu.ht.hotthink.error.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.BusinessException;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
