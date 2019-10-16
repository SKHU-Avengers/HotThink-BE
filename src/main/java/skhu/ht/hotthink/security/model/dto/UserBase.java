package skhu.ht.hotthink.security.model.dto;

import lombok.Getter;

public abstract class UserBase {
    @Getter
    protected String email;

    @Getter
    protected String nickName;
}
