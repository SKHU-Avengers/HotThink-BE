package skhu.ht.hotthink.config;

import lombok.*;
import java.util.Date;

    /*
      작성자: 김영곤
      작성일: 19-10-27
      내용: 응답을 이쁘게 반환할 클래스
    */

public class ResponseMessage{
    @Getter private final String status;
    @Getter private final Date time;
    @Getter private final String message;

    private ResponseMessage(final String message, final String status) {
        this.message = message;
        this.status = status;
        this.time = new java.util.Date();
    }

    public static ResponseMessage of(final String message, String status) {
        return new ResponseMessage(message, status);
    }
}
