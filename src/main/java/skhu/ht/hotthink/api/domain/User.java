package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import skhu.ht.hotthink.api.user.model.UserInfoDTO;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude={"Preference"})
@EqualsAndHashCode(exclude={"Preference"})
@Entity(name = "TB_USER")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long seq;
    protected String email;
    protected String nickName;
    @Column(name="UNAME")
    protected String name;
    protected String pw;
    @Enumerated(EnumType.STRING)
    protected RoleName auth;
    private String tel;
    @Column(name="UPOINT")
    private Integer point;
    private Integer realTicket;

    @JsonIgnore
    @OneToMany(mappedBy = "preference")
    private List<Preference> preferenceList;
}
