package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.InvalidValueException;

public class IdeaInvalidException extends InvalidValueException {
    public IdeaInvalidException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
    public IdeaInvalidException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
