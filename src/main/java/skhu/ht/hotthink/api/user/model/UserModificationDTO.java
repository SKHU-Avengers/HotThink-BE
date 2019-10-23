package skhu.ht.hotthink.api.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.ht.hotthink.api.domain.RoleName;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserModificationDTO extends UserBase {
    @Getter private String pw;
    @Getter private String tel;
    @Getter private List<String> preferences;
    @Getter private RoleName auth;
}
