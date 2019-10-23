package skhu.ht.hotthink.security.model.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class TokenError {
    @Getter private final HttpStatus status;
    @Getter private final Date time;
    @Getter private final String message;
    @Getter private final ErrorCode errorCode;

    public TokenError(final String message, final ErrorCode errorCode, final HttpStatus status){
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.time = new java.util.Date();
    }

    public static TokenError of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new TokenError(message, errorCode, status);
    }

    @AllArgsConstructor
    public enum ErrorCode{
        GLOBAL(2),
        AUTHENTICATION(10),
        JWT_TOKEN_EXPIRED(11);

        @Getter
        @JsonValue
        private int errorCode;
    }
}
