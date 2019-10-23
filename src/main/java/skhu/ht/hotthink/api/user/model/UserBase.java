package skhu.ht.hotthink.api.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class UserBase {
    protected String email;
    protected String nickName;
}
