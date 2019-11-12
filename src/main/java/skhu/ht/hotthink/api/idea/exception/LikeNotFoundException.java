package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.EntityNotFoundException;

public class LikeNotFoundException extends EntityNotFoundException {
    public LikeNotFoundException(String message) {
        super(message);
    }

    public LikeNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public LikeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
