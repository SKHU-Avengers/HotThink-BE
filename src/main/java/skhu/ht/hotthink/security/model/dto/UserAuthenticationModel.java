package skhu.ht.hotthink.security.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import skhu.ht.hotthink.api.user.model.UserBase;


@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthenticationModel extends UserBase {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Getter
    private String pw;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Getter
    private SimpleGrantedAuthority auth;

    @Builder
    public UserAuthenticationModel(String email, String pw, String nickName, SimpleGrantedAuthority auth){
        this.email = email;
        this.pw = pw;
        this.nickName = nickName;
        this.auth = auth;
    }
}
