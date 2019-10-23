package skhu.ht.hotthink.api.domain;

import lombok.Data;

import javax.persistence.*;
    /*
        작성자: 홍민석, 김영곤
        작성일: 19-10-23
        내용: Preference 필드 삭제
    */
@Data
@Entity(name = "User")
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
}
