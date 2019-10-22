package skhu.ht.hotthink.security.model.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class LoginError {
    @Getter private final HttpStatus status;
    @Getter private final Date time;
    @Getter private final String message;
    @Getter private final ErrorCode errorCode;

    public LoginError(final String message, final ErrorCode errorCode, final HttpStatus status){
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.time = new java.util.Date();
    }

    public static LoginError of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new LoginError(message, errorCode, status);
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
