package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

    private String tel;

    @Enumerated(EnumType.STRING)
    protected RoleName auth;

    @Column(name="UPOINT")
    private Integer point;
    private Integer realTicket;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Preference> preferences;

    @Enumerated(EnumType.STRING)
    private UseAt useAt;
}
