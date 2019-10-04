package skhu.ht.hotthink.api.user.model;

import lombok.Getter;
import lombok.Setter;

public class UserInfoDTO {
    @Getter @Setter private String email;
    @Getter @Setter private String nickName;
    @Getter @Setter private String tel;
    @Getter @Setter private String pw;
}
