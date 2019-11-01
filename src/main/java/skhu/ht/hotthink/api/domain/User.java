package skhu.ht.hotthink.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import skhu.ht.hotthink.api.domain.enums.RoleName;
import skhu.ht.hotthink.api.domain.enums.UseAt;

import javax.persistence.*;
import java.util.List;

/*
    작성자: 홍민석, 김영곤
    작성일: 19-10-23
    내용: Preference 필드 삭제
    작성일: 19-10-27
    내용: Preference 리스트 필드 다시 부활
*/
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

    @Column(name="USE_AT")
    @Enumerated(EnumType.STRING)
    private UseAt useAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    List<Preference> preferenceList;
}
