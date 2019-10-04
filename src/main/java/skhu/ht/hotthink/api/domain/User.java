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
    @Getter @Setter protected Long seq;
    @Getter @Setter protected String email;
    @Getter @Setter protected String nickName;
    @Getter @Setter protected String name;
    @Getter @Setter protected String pw;
    @Enumerated(EnumType.STRING)
    @Getter @Setter protected RoleName auth;
    @Getter @Setter private String tel;
    @Getter @Setter private int point;
    @Getter @Setter private int realTicket;
}
