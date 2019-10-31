package skhu.ht.hotthink.error.exception;

import skhu.ht.hotthink.error.ErrorCode;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
    public UnauthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
