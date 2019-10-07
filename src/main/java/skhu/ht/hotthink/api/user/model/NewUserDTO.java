package skhu.ht.hotthink.api.user.model;

import lombok.Data;

@Data
public class NewUserDTO {
    protected String email;
    protected String nickName;
    protected String name;
    protected String pw;
    private String tel;
}
