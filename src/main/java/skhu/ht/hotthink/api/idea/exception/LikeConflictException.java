package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.BusinessException;

public class LikeConflictException extends BusinessException {
    public LikeConflictException(String message) {
        super(message, ErrorCode.LIKE_CONFLICT);
    }
    public LikeConflictException() {
        super(ErrorCode.LIKE_CONFLICT);
    }
}
