package skhu.ht.hotthink.security.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import skhu.ht.hotthink.api.user.model.UserBase;

//로그인 필터에서 맵핑을 위한 모델
public class UserLoginRequestModel extends UserBase {
    @Getter
    private String pw;

    @JsonCreator
    public UserLoginRequestModel(@JsonProperty("email") String email, @JsonProperty("password") String pw){
        this.email = email;
        this.pw = pw;
    }
}
