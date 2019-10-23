package skhu.ht.hotthink.api;

public enum MessageState {
    Fail, //잘못된 입력값으로 조건에 안맞아 끝나는 경우
    Success, //목적 달성
    Created, //생성 성공
    Error, //에러발생
    Conflict, //중복데이터 발생시
    NotExist //존재하지 않음.
}
