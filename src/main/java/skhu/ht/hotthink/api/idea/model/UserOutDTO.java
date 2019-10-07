package skhu.ht.hotthink.api.idea.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.RoleName;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UserOutDTO {
    protected Long seq;
    protected String nickName;
    @Enumerated(EnumType.STRING)
    protected RoleName auth;
}
