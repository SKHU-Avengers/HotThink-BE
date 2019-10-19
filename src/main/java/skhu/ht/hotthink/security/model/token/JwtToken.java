package skhu.ht.hotthink.security.model.token;

/*
* 다형성으로 구현할 JwtToken 기본 인터페이스
*/
public interface JwtToken {
    String getToken();
}
