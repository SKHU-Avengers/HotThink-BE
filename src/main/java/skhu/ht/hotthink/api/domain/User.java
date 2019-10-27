package skhu.ht.hotthink.api.domain;

import lombok.Data;

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
@Entity(name = "TB_USER")
@Table(name = "TB_USER")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UR_SEQ")
    private Long seq;
    private String email;
    private String nickName;

    @Column(name="UNAME")
    private String name;
    private String pw;

    private String tel;

    @Enumerated(EnumType.STRING)
    private RoleName auth;

    @Column(name="UPOINT")
    private Integer point;
    private Integer realTicket;

    @Enumerated(EnumType.STRING)
    private UseAt useAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    List<Preference> preferenceList;
}
