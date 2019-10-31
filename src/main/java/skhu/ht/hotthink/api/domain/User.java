package skhu.ht.hotthink.api.domain;

import lombok.*;
import skhu.ht.hotthink.api.domain.enums.RoleName;
import skhu.ht.hotthink.api.domain.enums.UseAt;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "User")
@Table(name = "TB_USER")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UR_SEQ")
    protected Long seq;
    protected String email;
    protected String nickName;

    @Column(name="UNAME")
    protected String name;
    protected String pw;
    @Column(name="PROFILE_IMG")
    private String profileImg;
    private String tel;

    @Enumerated(EnumType.STRING)
    protected RoleName auth;

    @Column(name="UPOINT")
    private Integer point;
    private Integer realTicket;

    @OneToMany(mappedBy = "user")
    private List<Preference> preferences;

    @Column(name="USE_AT")
    @Enumerated(EnumType.STRING)
    private UseAt useAt;
}
