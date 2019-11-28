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
    작성일: 19-11-27
    내용: 구독정보 저장
*/
@Getter
@Entity(name = "User")
@Table(name = "TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UR_SEQ")
    protected Long seq;
    protected String email;
    @Setter
    protected String nickName;

    @Column(name="UNAME")
    protected String name;
    @Setter
    protected String pw;
    @Column(name="PROFILE_IMG")
    @Setter
    private String profileImg;
    @Setter
    private String tel;

    @Setter
    @Enumerated(EnumType.STRING)
    protected RoleName auth;

    @Setter
    @Column(name="UPOINT")
    private Integer point;

    @Setter
    private Integer realTicket;

    @Setter
    @Column(name="USE_AT")
    @Enumerated(EnumType.STRING)
    private UseAt useAt;

    @Setter
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    List<Preference> preferenceList;

    @OneToOne(mappedBy = "user")
    Subscribe subscribe;
}
