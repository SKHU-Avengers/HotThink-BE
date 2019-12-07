package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.InvalidValueException;

public class ReplyInvalidException extends InvalidValueException{
    public ReplyInvalidException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
    public ReplyInvalidException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }

}
