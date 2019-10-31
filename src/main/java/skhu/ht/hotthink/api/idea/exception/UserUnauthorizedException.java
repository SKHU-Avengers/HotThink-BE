package skhu.ht.hotthink.api.idea.exception;

import skhu.ht.hotthink.error.ErrorCode;
import skhu.ht.hotthink.error.exception.UnauthorizedException;

public class UserUnauthorizedException extends UnauthorizedException {
    public UserUnauthorizedException(String message) {
        super(message);
    }

    public UserUnauthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UserUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
