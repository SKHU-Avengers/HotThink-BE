package skhu.ht.hotthink.api.user.model;

import lombok.Getter;

public abstract class UserBase {
    @Getter
    protected String email;

    @Getter
    protected String nickName;
}
