package skhu.ht.hotthink.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import skhu.ht.hotthink.error.exception.BusinessException;


/*
    Spring 컨트롤러에서 발생하는 예외처리를 담당
    및 서비스 단에서 발생하는 예외를 ErrorResponse로 매핑.
 */
@Slf4j
@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    /*
        javax.validation.Valid or @Validated으로 binding error발생시 수행.
        HttpMessageConverter에서 등록한 httpMessageConverter 바인딩을 못할 경우 발생

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE,e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
     */

    /*
        Enum type 일치하지 않아 binding 실패시 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("handleMethodArgumentTypeMismatchException",e);
        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
        서비스단에서 예외 발생시 수행
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity handleBusinessException(final BusinessException e){
        final ErrorCode errorCode = e.getErrorCode();
        final String message = e.getMessage();
        final ErrorResponse response = ErrorResponse.of(errorCode, message);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
    /*
        개발자가 에러처리하지 못한 에러들을 담당.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e){
        log.error("handleException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
