package skhu.ht.hotthink.api.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Login {
    @Getter @Setter String loginId;
    @Getter @Setter String password;
}
