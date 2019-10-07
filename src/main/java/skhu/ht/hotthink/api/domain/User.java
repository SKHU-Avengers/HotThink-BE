package skhu.ht.hotthink.api.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import skhu.ht.hotthink.api.user.model.UserInfoDTO;

import javax.persistence.*;

@Data
@Entity(name = "TB_USER")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long seq;
    protected String email;
    protected String nickName;
    protected String name;
    protected String pw;
    @Enumerated(EnumType.STRING)
    protected RoleName auth;
    private String tel;
    private int point;
    private int realTicket;
}
