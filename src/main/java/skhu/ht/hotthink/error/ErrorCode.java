package skhu.ht.hotthink.error;

import lombok.Getter;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400,"C001","Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,"C002","Invalid Input Value"),
    ENTITY_NOT_FOUND(404,"C003","Entity Not Found"),
    INTERNAL_SERVER_ERROR(500,"C004","Server Error"),
    INVALID_TYPE_VALUE(400,"C005","Invalid Type Value"),
    UNAUTHORIZED(401,"C005","Unauthorized Access"),
    EMAIL_CONFLICT(409,"U001","이메일 중복"),
    NICKNAME_CONFLICT(409,"U002","닉네임 중복"),
    NOT_ENOUGH_MONEY(400,"P001","금액이 부족합니다.")

    ;
    /*
    Fail, //잘못된 입력값으로 조건에 안맞아 끝나는 경우
    Success, //목적 달성
    Created, //생성 성공
    Error, //에러발생
    Conflict, //중복데이터 발생시
    NotExist, //존재하지 않음.
     */
    @Getter private final String code;
    @Getter private final String message;
    @Getter private int status;
    ErrorCode(final int status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
