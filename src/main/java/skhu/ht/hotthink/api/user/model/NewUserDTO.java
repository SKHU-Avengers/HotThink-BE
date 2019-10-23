package skhu.ht.hotthink.api.user.model;

import lombok.Data;
import skhu.ht.hotthink.api.domain.Preference;

import java.util.List;

@Data
public class NewUserDTO {
    protected String email;
    protected String nickName;
    protected String name;
    protected String pw;
    private String tel;
    private List<String> preferences;
}
